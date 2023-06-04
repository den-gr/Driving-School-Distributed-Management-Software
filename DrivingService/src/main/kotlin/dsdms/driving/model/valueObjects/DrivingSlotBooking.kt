package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable


/**
 * Information that allows to book a driving slot.
 * @param date when driving slot should be booked
 * @param time
 * @param instructorId of instructor that will be responsible for the driving
 * @param dossierId of subscriber
 * @param drivingSlotType that define if it is a lesson or exam
 * @param licensePlate of vehicle
 */
@Serializable
data class DrivingSlotBooking(
    val date: LocalDate,
    val time: LocalTime,
    val instructorId: String,
    val dossierId: String,
    val drivingSlotType: DrivingSlotType,
    val licensePlate: LicensePlate,
)


/**
 * Types of driving slots.
 */
enum class DrivingSlotType {
    ORDINARY,
    EXAM,
}
