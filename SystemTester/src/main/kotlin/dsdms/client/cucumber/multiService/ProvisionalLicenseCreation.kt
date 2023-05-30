package dsdms.client.cucumber.multiService

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import dsdms.exam.model.entities.ProvisionalLicense
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/multiService/provisionalLicenseTest.feature"],
    plugin = ["pretty", "summary"]
)
class ProvisionalLicenseCreation : En {
    private val dossierService: WebClient = VertxProviderImpl().getDossierServiceClient()
    private val examService: WebClient = VertxProviderImpl().getExamServiceClient()
    private var statusCode: Int? = null
    private var dossierId: String? = null
    private var examDate: LocalDate? = null

    init {
        val sleeper = SmartSleep()
        Given("a new registered dossier") {
            val request = dossierService
                .post("/dossiers")
                .sendBuffer(createJson(SubscriberDocuments("name", "surname", LocalDate.parse("1990-01-01"), "FISCALCODE")))
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(HTTP_OK, response?.statusCode())
            dossierId = response?.body().toString()
        }
        And("a new exam appeal on {word}") {date: String ->
            examDate = LocalDate.parse(date)
            //todo
        }
        When("the dossier is registered to exam appeal") {
            //todo
        }
        Then("the subscriber passes the theoretical exam so secretary creates new provisional license") {
            val request = examService
                .post("/provisionalLicences")
                .sendBuffer(createJson(ProvisionalLicense("d1", examDate!!) ))
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(HTTP_OK, response?.statusCode())
        }

    }
}