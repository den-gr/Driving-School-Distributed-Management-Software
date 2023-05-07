import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import dsdms.driving.model.valueObjects.licensePlate.LicensePlateInit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json
import kotlin.test.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocalTest{
    @Test fun regexTestShouldThrowException() {
        assertThrows<IllegalArgumentException> { LicensePlateInit("FZ340ARR") }
    }

    @Test fun regexTest() {
        val licensePlate: LicensePlate = LicensePlateInit("FZ340AR")
        assertTrue(licensePlate.verifyStructure())
    }

    @Test fun localDateSerialization() {
        val json = Json.encodeToString(LocalDate.serializer(), LocalDate(2013, 12, 4))
        println(json)

        val prova = Json.encodeToString(GetDrivingSlotDocs.serializer(), GetDrivingSlotDocs(LocalDate.parse("2013-12-04")))

        assertEquals(prova,"{\"date\":\"2013-12-04\"}")
    }

    @Test fun localTimeSerialization() {
        val json = Json.encodeToString(LocalTime.serializer(), LocalTime(9, 30))
        println(json)

        val prova = Json.encodeToString(LocalTime.serializer(), LocalTime.parse("09:30"))

        assertEquals(prova,"\"09:30\"")
    }

}