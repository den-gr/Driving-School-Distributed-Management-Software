package dsdms.doctor.model.entities

import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class DoctorSlot(
    val date: String,
    val time: String,
    val dossierId: String
)


enum class DoctorDays() {
    TUESDAY,
    FRIDAY
}

enum class DoctorTimeSlot(val time: LocalTime) {
    InitTime(LocalTime.parse("18:00")),
    FinishTime(LocalTime.parse("19:30"))
}