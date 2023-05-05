package dsdms.driving.model.domainServices

import dsdms.driving.database.Repository
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs

class DrivingService(private val repository: Repository) {
//    fun saveNewDrivingSlot(documents: DrivingSlot): String? {
//        return repository.createDrivingSlot(documents)
//    }

//    fun verifyDrivingSlotDocuments(documents: DrivingSlot): DomainResponseStatus {
//        return DomainResponseStatus.OK
//    }

    fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot> {
        return repository.getOccupiedDrivingSlots(docs)
    }
}