package dsdms.driving.model.domainServices

import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs

interface DrivingService {
    suspend fun saveNewDrivingSlot(documents: DrivingSlotBooking): String?

    suspend fun verifyDocuments(drivingSlotBooking: DrivingSlotBooking): DomainResponseStatus

    suspend fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot>
}