package dsdms.client.cucumber.exam

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.valueObjects.TheoreticalExamAppealUpdate
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/exam/theoreticalExamTest.feature"],
    plugin = ["pretty", "summary"]
)
class TheoreticalExamTestEvent : En {
    private val client: WebClient = VertxProviderImpl().getExamServiceClient()
    private var statusMessage: String = ""
    private var statusCode: Int? = null
    private var examAppealList: List<String>? = null

    init {
        val sleeper = SmartSleep()

        When("secretary inserts a new day {word} and number of places {int} for exam appeal") { date: String, places: Int ->
            val request = client
                .post("/theoreticalExam/examAppeal")
                .sendBuffer(createJson(TheoreticalExamAppeal(date, places)))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            statusCode = response?.statusCode()
            statusMessage = response?.body().toString()
        }
        Then("it receives a result with code {int} and message {word}") { code: Int, message: String ->
            assertEquals(code, statusCode)
            assertEquals(message, statusMessage)
        }

        Given("list of future exam appeals") {
            val request = client
                .get("/theoreticalExam/examAppeal")
                .send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            statusCode = response?.statusCode()
            statusMessage = response?.body().toString()
        }
        Then("secretary request info about one specific appeal in 2023-10-12") {
            assertEquals(HTTP_OK, statusCode)
            examAppealList = Json
                .decodeFromString(ListSerializer(TheoreticalExamAppeal.serializer()), statusMessage)
                .find { el -> el.date == "2023-10-12" }?.registeredDossiers
        }
        And("it receives empty list of dossier ids") {
            assertEquals(listOf(), examAppealList)
        }

        When("secretary registers dossier {word} in exam appeal in date {word}") { dossierId: String, date: String ->
            val request = client
                .put("/theoreticalExam/examAppeal")
                .sendBuffer(createJson(TheoreticalExamAppealUpdate(dossierId, date)))
            val response = sleeper.waitResult(request)

            checkResponse(response)

            statusCode = response?.statusCode()
            statusMessage = response?.body().toString()
        }
        Then("receives code {int} with message {word}") { code: Int, message: String ->
            assertEquals(code, statusCode)
            assertEquals(message, statusMessage)
        }
    }
}
