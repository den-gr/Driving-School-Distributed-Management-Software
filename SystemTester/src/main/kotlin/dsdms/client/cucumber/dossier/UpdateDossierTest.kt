package dsdms.client.cucumber.dossier

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/dossier/updateDossier.feature"],
    plugin = ["pretty", "summary"]
)
class UpdateDossierTest : En {
    private val client: WebClient = VertxProviderImpl().getDossierServiceClient()
    private var value: String = ""
    private var retrievedDossier: Dossier? = null
    private var result: HttpResponse<Buffer>? = null

    init {
        val sleeper = SmartSleep()

        Given("a new registered dossier: {word}, {word}, {word}, {word}") { name: String, surname: String, birthdate: String, fc: String ->
            val request = client
                .post("/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, LocalDate.parse(birthdate), fc)))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            value = response?.body().toString()
        }

        Then("i request the dossier from server with obtained id") {
            val request = client
                .get("/dossiers/$value")
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)

            retrievedDossier = Json.decodeFromString(response?.body().toString())
        }

        When("i read his {word} exam status is false") { type: String ->
            assertNotNull(retrievedDossier)
            if (type == "theoretical") {
                retrievedDossier?.examStatus?.let { assertFalse(it.theoretical) }
            } else {
                retrievedDossier?.examStatus?.let { assertFalse(it.practical) }
            }
        }

        Then("trying to update {word} exam status to true") { type: String ->
            val request = client
                .put("/dossiers/$value/examStatus")
                .sendBuffer(createJson(ExamStatusUpdate(type, true)))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            result = response
        }

        And("obtaining {word} exam status true as response from server") { type: String ->
            assertNotNull(result)
            assertEquals(HTTP_OK, result?.statusCode())

            val request = client
                .get("/dossiers/$value")
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            retrievedDossier = Json.decodeFromString(response?.body().toString())
            if (type == "theoretical") {
                retrievedDossier?.examStatus?.let { assertTrue(it.theoretical) }
            } else {
                retrievedDossier?.examStatus?.let { assertTrue(it.practical) }
            }
        }
    }
}
