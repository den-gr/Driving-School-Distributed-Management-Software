package dsdms.client.cucumber.dossier

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/dossier/registerAndReadDossier.feature"],
    plugin = ["pretty", "summary"]
)
class DossierTest : En {
    private val client: WebClient = VertxProviderImpl().getDossierServiceClient()
    private var value: String = ""
    private var retrievedDossier: Dossier? = null

    private var statusCode: Int? = null

    init {
        val sleeper = SmartSleep()
        When("I send {word}, {word},{word},{word} to server") { name: String, surname: String, birthdate: String, fiscal_code: String ->
            val request = client
                .post("/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, LocalDate.parse(birthdate), fiscal_code)))
            val response = sleeper.waitResult(request)

            checkResponse(response)

            statusCode = response?.statusCode()
            value = response?.body().toString()
        }
        Then("I received id of registered dossier") {
            assertEquals(HTTP_OK, statusCode)
        }

        When("I send id to server") {
            val request = client
                .get("/dossiers/$value")
                .send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            assertEquals(HTTP_OK, response?.statusCode())

            retrievedDossier = Json.decodeFromString(response?.body().toString())
        }

        Then("I received {word},{word},{word},{word} of registered dossier") { name: String, surname: String, birthdate: String, fiscal_code: String ->
            assertNotNull(retrievedDossier)
            assertEquals(name, retrievedDossier?.name)
            assertEquals(surname, retrievedDossier?.surname)
            assertEquals(birthdate, retrievedDossier?.birthdate)
            assertEquals(fiscal_code, retrievedDossier?.fiscal_code)
        }

        When("I send bad informations {word}, {int}, {word}, {word} to server") { name: String, surname: Int, birthdate: String, fiscal_code: String ->
            val request = client
                .post("/dossiers")
                .sendJson(
                    JsonObject.of(
                        "name",
                        name,
                        "surname",
                        surname,
                        "birthdate",
                        birthdate,
                        "fiscal_code",
                        fiscal_code
                    )
                )
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            statusCode = response.statusCode()
        }

        Then("I received Bad request error message") {
            assertNotNull(statusCode)
            assertEquals(HTTP_BAD_REQUEST, statusCode)
//            assertEquals(DomainResponseStatus.)
        }

        When("I send duplicated informations {word},{word},{word},{word} to server") { name: String, surname: String, birthdate: String, fiscal_code: String ->
            val request = client
                .post("/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, LocalDate.parse(birthdate), fiscal_code)))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            statusCode = response.statusCode()
        }

        Then("I received bad request error message") {
            assertNotNull(statusCode)
            assertEquals(HTTP_BAD_REQUEST, statusCode)
        }
    }
}