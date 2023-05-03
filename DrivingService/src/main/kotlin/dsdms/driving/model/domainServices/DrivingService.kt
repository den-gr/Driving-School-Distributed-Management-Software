package dsdms.driving.model.domainServices

import dsdms.driving.database.Repository
import dsdms.driving.model.entities.DrivingSlot

class DrivingService(private val repository: Repository) {
    fun saveNewDrivingSlot(documents: DrivingSlot): String? {
        return repository.createDrivingSlot(documents)
    }

    fun verifyDrivingSlotDocuments(documents: DrivingSlot): DomainResponseStatus {
        return DomainResponseStatus.OK
    }
}