package dsdms.driving.model.domainServices

import dsdms.driving.database.Repository
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs

class DrivingService(private val repository: Repository) {
    fun saveNewDrivingSlot(documents: DrivingSlotBooking): String? {
        return repository.createDrivingSlot(createRegularDrivingSlot(documents))
    }

    private fun createRegularDrivingSlot(documents: DrivingSlotBooking): DrivingSlot {
        return DrivingSlot(
            documents.date.toString(),
            documents.time.toString(),
            documents.instructorId,
            documents.dossierId,
            documents.licensePlate,
            documents.drivingSlotType
        )
    }

    fun verifyDocuments(drivingSlotBooking: DrivingSlotBooking): DomainResponseStatus {
        val futureDrivingSlots: List<DrivingSlot> = repository.getFutureDrivingSlots()

        val dateAndTime: (DrivingSlot) -> Boolean = { el ->
            el.date == drivingSlotBooking.date.toString() && el.time == drivingSlotBooking.time.toString() }

        val forThisInstructor: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.instructorId == drivingSlotBooking.instructorId }

        val forThisVehicle: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.licensePlate.toString() == drivingSlotBooking.licensePlate.toString() }

        val forThisDossier: (DrivingSlot) -> Boolean = { el -> el.dossierId == drivingSlotBooking.dossierId }

        return if (futureDrivingSlots.any(forThisDossier))
            DomainResponseStatus.OCCUPIED_DRIVING_SLOTS
        else if (futureDrivingSlots.any(forThisInstructor))
            DomainResponseStatus.INSTRUCTOR_NOT_FREE
        else if (futureDrivingSlots.any(forThisVehicle))
            DomainResponseStatus.VEHICLE_NOT_FREE
        else DomainResponseStatus.OK
    }

    fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot> {
        return repository.getOccupiedDrivingSlots(docs)
    }
}
