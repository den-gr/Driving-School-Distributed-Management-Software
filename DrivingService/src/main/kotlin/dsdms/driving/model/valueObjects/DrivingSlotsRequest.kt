package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Allows request a list of driving slots.
 * @param date of driving slots
 * @param instructorId not null if we are interested on driving of a particular instructor
 */
@Serializable
data class DrivingSlotsRequest(
    val date: LocalDate,
    val instructorId: String? = null,
)
