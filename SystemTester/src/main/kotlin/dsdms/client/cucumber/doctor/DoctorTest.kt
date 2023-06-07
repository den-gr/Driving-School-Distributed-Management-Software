package dsdms.client.cucumber.doctor

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.doctor.model.entities.DoctorSlot
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Doctor tests implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/doctor/doctorTest.feature"],
    plugin = ["pretty", "summary"],
)
class DoctorTest : En {
    private val client: WebClient = VertxProviderImpl().getDoctorServiceClient()
    private var receivedCode: Int? = null
    private var receivedMessage: String = ""

    init {
        val sleeper = SmartSleep()
        When(
            "sending {word} for registering doctor visit on {word} and {word}",
        ) { id: String, date: String, time: String ->
            val request = client
                .post("/doctorSlots")
                .sendBuffer(createJson(DoctorSlot(date, time, id)))
            val response = sleeper.waitResult(request)

            checkResponse(response)

            receivedCode = response?.statusCode()
            receivedMessage = response?.body().toString()
        }
        Then("secretary receives {word} with {int}") { message: String, code: Int ->
            assertEquals(message, receivedMessage)
            assertEquals(code, receivedCode)
        }

        When("it sends {word}") { data: String ->
            val request = client
                .get("/doctorSlots/$data").send()
            val response = sleeper.waitResult(request)

            checkResponse(response)

            receivedCode = response?.statusCode()
            receivedMessage = response?.body().toString()
        }
        Then("receives list of doctor slots, containing one doctor slot for dossier {word} at {word}") {
                id: String, time: String ->
            assertEquals(HTTP_OK, receivedCode)
            val doctorSlots: List<DoctorSlot> =
                Json.decodeFromString(ListSerializer(DoctorSlot.serializer()), receivedMessage)
            assertTrue(doctorSlots.isNotEmpty())
            assertEquals(1, doctorSlots.size)
            assertEquals(DoctorSlot("2023-09-19", time, id), doctorSlots[0])
        }
    }
}
