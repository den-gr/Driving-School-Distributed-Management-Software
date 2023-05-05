package dsdms.driving.database

import com.mongodb.client.MongoDatabase
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import org.litote.kmongo.*

class RepositoryImpl(drivingService: MongoDatabase): Repository {
    private val drivingSlots = drivingService.getCollection<DrivingSlot>("DrivingSlot")

    override fun createDrivingSlot(newDrivingSlot: DrivingSlot): String? {
        return newDrivingSlot.apply { drivingSlots.insertOne(newDrivingSlot) }._id
    }

    override fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): DrivingSlot? {
        return drivingSlots.findOne(DrivingSlot::date eq docs.date)
       // }
        //else
            //drivingSlots.find(and(DrivingSlot::date eq docs.date, DrivingSlot::instructorId eq docs.instructorId)).toList()
    }
}