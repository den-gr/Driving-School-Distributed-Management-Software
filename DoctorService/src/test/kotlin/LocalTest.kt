
import dsdms.doctor.model.entities.DoctorDays
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class LocalTest {
    @Test
    fun assertDayOfWeekComparison() {
        val localDate = LocalDate.parse("2023-09-22")

        assertTrue(checkIfContains(localDate.dayOfWeek.name))
        assertFalse(checkIfContains(LocalDate.parse("2023-09-23").dayOfWeek.name))
    }

    private fun checkIfContains(dayOfWeek: String): Boolean {
        return DoctorDays.values().any { el -> el.name == dayOfWeek }
    }
}
