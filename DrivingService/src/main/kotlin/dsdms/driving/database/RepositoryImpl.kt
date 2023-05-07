package dsdms.driving.database

import com.mongodb.client.MongoDatabase
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class RepositoryImpl(drivingService: MongoDatabase) : Repository {
    private val drivingSlots = drivingService.getCollection<DrivingSlot>("DrivingSlot")

    override fun createDrivingSlot(newDrivingSlot: DrivingSlot): String? {
        return newDrivingSlot.apply { drivingSlots.insertOne(newDrivingSlot) }._id
    }

    override fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot> {
        return if (docs.instructorId == null) {
            drivingSlots.find(DrivingSlot::date eq docs.date).toList()
        } else {
            drivingSlots.find(and(DrivingSlot::date eq docs.date, DrivingSlot::instructorId eq docs.instructorId)).toList()
        }
    }
}
