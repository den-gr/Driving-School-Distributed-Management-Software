package dsdms.client.cucumber.multiService

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.PracticalExamState
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import kotlin.test.assertEquals

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/multiService/provisionalLicenseUpdate.feature"],
    plugin = ["pretty", "summary"]
)
class ProvisionalLicenseUpdates : En {
    private val dossierService: WebClient = VertxProviderImpl().getDossierServiceClient()
    private val examService: WebClient = VertxProviderImpl().getExamServiceClient()
    private val sleeper = SmartSleep()

    private var dossierId: String? = null

    init {
        Given("dossier {word} that is associated with a valid provisional license") {id: String ->
            dossierId = id
            val request = examService.get("/provisionalLicences/$dossierId").send()
            val response = sleeper.waitResult(request)
            assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        }
        When("register {word} state for practical exam") { state: String ->
            val request = examService
                .put("/provisionalLicences/$dossierId")
                .addQueryParam("practicalExamUpdate", state)
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        }
        Then("provisional license is deleted so {word}") {attendedResponse: String ->
            val request = examService.get("/provisionalLicences/$dossierId").send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(attendedResponse, response?.body().toString())
            assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response?.statusCode())
        }
        And("dossier practical exam status is {word}") {finalState: String ->
            val request = dossierService.get("/dossiers/$dossierId").send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
            val dossier: Dossier = Json.decodeFromString(response?.body().toString())
            assertEquals(PracticalExamState.valueOf(finalState), dossier.examsStatus.practicalExamState)
        }
    }
}