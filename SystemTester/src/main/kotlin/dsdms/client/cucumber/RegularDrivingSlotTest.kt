package dsdms.client.cucumber

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/drivingSlotBooking.feature"],
    plugin = ["pretty", "summary"]
)
class RegularDrivingSlotTest : En {
    private val client: WebClient = VertxProviderImpl().getDrivingServiceClient()
    private var value: List<DrivingSlot>? = null

    init {
        val sleeper = SmartSleep()

        When("i request occupied driving slots in a {word}")
        { date: String ->
            val request = client
                .get("/drivingSlots")
                .sendBuffer(createJson(GetDrivingSlotDocs(LocalDate.parse(date))))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            val statusCode = response?.statusCode()
            println("BEfore $statusCode")
            assertEquals(HTTP_OK, statusCode)
            println("after $statusCode")
            value = Json.decodeFromString(ListSerializer(DrivingSlot.serializer()), response?.body().toString())
        }
        Then("the first driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}")
        { date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(0)?.date.toString())
            assertEquals(time, value?.get(0)?.time.toString())
            assertEquals(instructorId, value?.get(0)?.instructorId)
            assertEquals(dossierId, value?.get(0)?.dossierId)
            assertEquals(vehicle, value?.get(0)?.vehicle.toString())
        }
        Then("the second driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}")
        { date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(1)?.date.toString())
            assertEquals(time, value?.get(1)?.time.toString())
            assertEquals(instructorId, value?.get(1)?.instructorId)
            assertEquals(dossierId, value?.get(1)?.dossierId)
            assertEquals(vehicle, value?.get(1)?.vehicle.toString())
        }
        Then("the third driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}")
        { date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(2)?.date.toString())
            assertEquals(time, value?.get(2)?.time.toString())
            assertEquals(instructorId, value?.get(2)?.instructorId)
            assertEquals(dossierId, value?.get(2)?.dossierId)
            assertEquals(vehicle, value?.get(2)?.vehicle.toString())
        }
    }
}