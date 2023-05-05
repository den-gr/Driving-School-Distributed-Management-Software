import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import dsdms.driving.model.valueObjects.licensePlate.LicensePlateInit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.json.Json

class LocalTest: FunSpec({

    test("regex test should throw exception") {
        shouldThrow<IllegalArgumentException> { LicensePlateInit("FZ340ARR") }
    }

    test("regex test") {
        val licensePlate: LicensePlate = LicensePlateInit("FZ340AR")
        licensePlate.verifyStructure() shouldBe true
    }

    test("local date serialization") {
        val json = Json.encodeToString(LocalDate.serializer(), LocalDate(2013, 12, 4))
        println(json)

        val prova = Json.encodeToString(GetDrivingSlotDocs.serializer(), GetDrivingSlotDocs(LocalDate.parse("2013-12-04")))
        println(prova)

        2 shouldBe 2
    }

    test("local time serialization") {
        val json = Json.encodeToString(LocalTime.serializer(), LocalTime(9, 30))
        println(json)

        val prova = Json.encodeToString(LocalTime.serializer(), LocalTime.parse("09:30"))
        println(prova)

        2 shouldBe 2
    }

})