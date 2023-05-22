package dsdms.driving.model.entities

import dsdms.driving.model.valueObjects.DrivingSlotType
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class DrivingSlot(
    val date: String,
    val time: String,
    val instructorId: String,
    val dossierId: String,
    val licensePlate: LicensePlate,
    val slotType: DrivingSlotType,
    @Contextual val _id: String? = null,
)