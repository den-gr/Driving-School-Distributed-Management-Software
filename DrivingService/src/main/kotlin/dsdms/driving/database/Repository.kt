package dsdms.driving.database

import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate

interface Repository {
    fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?

    fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot>

    fun getFutureDrivingSlots(): List<DrivingSlot>

    fun doesVehicleExist(licensePlate: LicensePlate): Boolean

    fun doesInstructorExist(instructorId: String): Boolean
}
