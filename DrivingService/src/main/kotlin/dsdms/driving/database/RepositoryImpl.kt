package dsdms.driving.database

import com.mongodb.client.MongoDatabase
import dsdms.driving.model.entities.DrivingSlot
import org.litote.kmongo.getCollection

class RepositoryImpl(drivingService: MongoDatabase): Repository {
    private val drivingSlots = drivingService.getCollection<DrivingSlot>("DrivingSlot")

    override fun createDrivingSlot(newDrivingSlot: DrivingSlot): String? {
        return newDrivingSlot.apply { drivingSlots.insertOne(newDrivingSlot) }._id
    }
}