import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalTest {
    @Test fun regexTestShouldThrowException() {
        assertThrows<IllegalArgumentException> { LicensePlate("FZ340ARR") }
    }

    @Test fun regexTest() {
        assertDoesNotThrow { LicensePlate("FZ340AR") }
        println(LicensePlate("FZ340AR"))
    }

    @Test fun localDateSerialization() {
        val json = Json.encodeToString(LocalDate.serializer(), LocalDate(2013, 12, 4))
        println(json)

        val prova = Json.encodeToString(DrivingSlotsRequest.serializer(), DrivingSlotsRequest(LocalDate.parse("2013-12-04")))

        assertEquals(prova, "{\"date\":\"2013-12-04\"}")
    }

    @Test fun localTimeSerialization() {
        val json = Json.encodeToString(LocalTime.serializer(), LocalTime(9, 30))
        println(json)

        val prova = Json.encodeToString(LocalTime.serializer(), LocalTime.parse("09:30"))

        assertEquals(prova, "\"09:30\"")
    }

    @Test fun localDateTime() {
        val json = Json.encodeToString(LocalTime.serializer(), LocalTime(9, 30))
        println(json)

        val prova = Json.encodeToString(LocalTime.serializer(), LocalTime.parse("09:30"))

        assertEquals(prova, "\"09:30\"")
    }
}
