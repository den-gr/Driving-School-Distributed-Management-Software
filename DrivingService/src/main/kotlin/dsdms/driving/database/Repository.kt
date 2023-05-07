package dsdms.driving.database

import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs

interface Repository {
    fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?

    fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot>
}
