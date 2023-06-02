package dsdms.client.cucumber.dossier

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.*
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/dossier/updateDossier.feature"],
    plugin = ["pretty", "summary"]
)
class UpdateDossierTest : En {
    private val client: WebClient = VertxProviderImpl().getDossierServiceClient()
    private var dossier: String = ""
    private var retrievedDossier: Dossier? = null
    private var result: HttpResponse<Buffer>? = null

    init {
        val sleeper = SmartSleep()

        Given("an already registered dossier with id {word}") { dossierId: String ->
            dossier = dossierId
            val request = client
                .get("/dossiers/$dossierId")
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)

            retrievedDossier = Json.decodeFromString(response?.body().toString())
        }

        When("i read his {word} exam progress state is {word}") { type: String, state: String->
            assertNotNull(retrievedDossier)
            if (type == ExamEvent.THEORETICAL_EXAM_PASSED.name) {
                retrievedDossier?.examsStatus?.let { assertEquals(TheoreticalExamState.valueOf(state), it.theoreticalExamState) }
            } else {
                retrievedDossier?.examsStatus?.let { assertEquals(PracticalExamState.valueOf(state), it.practicalExamState) }
            }
        }

        Then("trying to register {word} exam state as passed") { type: String->
            val request = client
                .put("/dossiers/$dossier/examStatus")
                .sendBuffer(Buffer.buffer(type))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            result = response
        }

        And("obtaining {word} exam state equal to {word}") { type: String, newState: String->
            assertNotNull(result)
            assertEquals(HTTP_OK, result?.statusCode())

            val request = client
                .get("/dossiers/$dossier")
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            retrievedDossier = Json.decodeFromString(response?.body().toString())
            if (type == ExamEvent.THEORETICAL_EXAM_PASSED.name) {
                retrievedDossier?.examsStatus?.let { assertEquals(TheoreticalExamState.valueOf(newState), it.theoreticalExamState) }
            } else {
                retrievedDossier?.examsStatus?.let { assertEquals(PracticalExamState.valueOf(newState), it.practicalExamState) }
            }
        }
    }
}
