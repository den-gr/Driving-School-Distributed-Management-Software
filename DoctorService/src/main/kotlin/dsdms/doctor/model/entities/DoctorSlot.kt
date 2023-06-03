package dsdms.doctor.model.entities

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

/**
 * Register information about doctor visit.
 * @param date of visit
 * @param time of visit
 * @param dossierId
 */
@Serializable
data class DoctorSlot(
    val date: String,
    val time: String,
    val dossierId: String,
)

/**
 * Days when there can be visits to doctor.
 */
enum class DoctorDays {
    TUESDAY,
    FRIDAY,
}

/**
 * Time when start and finises doctor visits are always the same.
 * @param time
 */
enum class DoctorTimeSlot(val time: LocalTime) {
    InitTime(LocalTime.parse("18:00")),
    FinishTime(LocalTime.parse("19:30")),
}
