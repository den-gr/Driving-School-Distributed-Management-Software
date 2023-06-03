package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DrivingSlotsRequest(
    val date: LocalDate,
    val instructorId: String? = null,
)
