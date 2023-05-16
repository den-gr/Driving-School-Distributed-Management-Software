package dsdms.doctor.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class GetBookedDoctorSlots(
    val date: String
)