package client.cucumber

import client.getWebClient
import dsdms.client.utils.createJson
import dsdms.client.utils.SmartSleep
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.SubscriberDocuments
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.json.JsonObject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import java.net.HttpURLConnection.*
import kotlin.test.assertNotNull

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/registerAndReadSubscriber.feature"],
    plugin = ["pretty", "summary"]
)
class DossierTest : En {
    private var value: Int = -1
    private var retrievedDossier: Dossier? = null

    init {
        val sleeper = SmartSleep()
        When("I send {word}, {word}, {word} to server") {name: String, surname: String, fiscal_code: String ->
            val request = getWebClient()
                .post(8000, "localhost", "/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, fiscal_code)))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            value = response.body().toString().toInt()
        }
        Then("I received {int} of registered dossier") { id: Int ->
            println("Value: $value")
            assertEquals(id, value) // first dossier registered
        }

        When("I send {int} to server") { id: Int ->
            val request = getWebClient()
                .get(8000, "localhost", "/dossiers/$id")
                .send()
            val response = sleeper.waitResult(request)
            assertNotNull(response)

            retrievedDossier = Json.decodeFromString(response.body().toString())
        }

        Then("I received {word}, {word}, {word} of registered dossier") {name: String, surname: String, fiscal_code: String ->
            assertNotNull(retrievedDossier)
            assertEquals(name, retrievedDossier?.name)
            assertEquals(surname, retrievedDossier?.surname)
            assertEquals(fiscal_code, retrievedDossier?.fiscal_code)
        }

        When("I send bad informations {word}, {int}, {word} to server") {name: String, surname: Int, fiscal_code: String ->
            val request = getWebClient()
                .post(8000, "localhost", "/dossiers")
                .sendJson(JsonObject.of(
                    "name", name,
                    "surname", surname,
                    "fiscal_code", fiscal_code))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            value = response.statusCode()
        }

        Then("I received Bad request error message") {
            assertNotNull(value)
            assertEquals(HTTP_BAD_REQUEST, value)
        }

        When("I send duplicated informations {word}, {word}, {word} to server") {name: String, surname: String, fiscal_code: String ->
            val request = getWebClient()
                .post(8000, "localhost", "/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, fiscal_code)))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            value = response.statusCode()
        }

        Then("I received Conflict error message") {
            assertNotNull(value)
            assertEquals(HTTP_CONFLICT, value)
        }
    }
}