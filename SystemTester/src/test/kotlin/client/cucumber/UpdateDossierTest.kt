package client.cucumber

import client.utils.createJson
import dsdms.client.utils.SmartSleep
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.ExamStatusUpdate
import dsdms.dossier.model.SubscriberDocuments
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/updateSubscriber.feature"],
    plugin = ["pretty", "summary"]
)
class UpdateDossierTest : En {
    private var value: String = ""
    private var retrievedDossier: Dossier? = null
    private var result: HttpResponse<Buffer>? = null

    init {
        val sleeper = SmartSleep()

        Given("a new registered dossier: {word}, {word}, {word}") { name: String, surname: String, fc: String ->
            val request = client
                .post(8000, "localhost", "/dossiers")
                .sendBuffer(createJson(SubscriberDocuments(name, surname, fc)))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            value = response.body().toString()
        }

        Then("i request the dossier from server with obtained id") {
            val request = client
                .get(8000, "localhost", "/dossiers/$value")
                .send()
            val response = sleeper.waitResult(request)
            assertNotNull(response)

            retrievedDossier = Json.decodeFromString(response.body().toString())
        }

        When("i read his {word} exam status is false") { type: String ->
            assertNotNull(retrievedDossier)
            if (type == "theoretical") {
                retrievedDossier?.examStatus?.let { assertFalse(it.getTheoretical()) }
            } else retrievedDossier?.examStatus?.let { assertFalse(it.getPractical()) }
        }

        Then("trying to update {word} exam status to true") { type: String  ->
            val request = client
                .put(8000, "localhost", "/dossiers/$value")
                .sendBuffer(createJson(ExamStatusUpdate(type, true)))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            result = response
        }

        And("obtaining {word} exam status true as response from server") { type: String ->
            assertNotNull(result)
            println("Update result: " + result?.body().toString())
            assertEquals(HTTP_OK, result?.statusCode())
        }
    }
}