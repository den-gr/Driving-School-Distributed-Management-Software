package dsdms.driving.database

import dsdms.driving.model.entities.DrivingSlot


interface Repository {
    fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?
}