package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Represents the info necessary to get driving slots from repository
 * @param date
 * @param instructorId: optional, default to null
 */
@Serializable
data class GetDrivingSlotDocs(
    val date: LocalDate,
    val instructorId: String? = null
)
