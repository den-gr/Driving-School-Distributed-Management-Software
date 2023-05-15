package dsdms.driving.database

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate

interface Repository {
    suspend fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?

    suspend fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot>

    suspend fun getFutureDrivingSlots(): List<DrivingSlot>

    suspend fun doesVehicleExist(licensePlate: LicensePlate): Boolean

    suspend fun doesInstructorExist(instructorId: String): Boolean

    suspend fun deleteDrivingSlot(drivingSlotId: String): RepositoryResponseStatus
}
