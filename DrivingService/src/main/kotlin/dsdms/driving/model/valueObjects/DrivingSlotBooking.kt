package dsdms.driving.model.valueObjects

import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class DrivingSlotBooking(
    val date: LocalDate,
    val time: LocalTime,
    val instructorId: String,
    val dossierId: String,
    val drivingSlotType: DrivingSlotType,
    val licensePlate: LicensePlate
)

enum class DrivingSlotType {
    ORDINARY,
    EXAM
}