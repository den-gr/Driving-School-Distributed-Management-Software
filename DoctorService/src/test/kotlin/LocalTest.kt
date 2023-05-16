
import dsdms.doctor.model.entities.DoctorDays
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue

internal class LocalTest {
    @Test
    fun assertDayOfWeekComparison() {
        val stringDate = "2023-09-22"
        val localDate = LocalDate.parse(stringDate)

        val dayOfWeek: String = localDate.dayOfWeek.name
        println(dayOfWeek)

        assertTrue(checkIfContains(dayOfWeek))
    }

    private fun checkIfContains(dayOfWeek: String): Boolean {
        return DoctorDays.values().any { el -> el.name == dayOfWeek}
    }
}
