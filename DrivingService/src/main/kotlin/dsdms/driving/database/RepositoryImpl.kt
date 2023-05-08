package dsdms.driving.database

import com.mongodb.client.MongoDatabase
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import org.litote.kmongo.*
import java.time.LocalDate

class RepositoryImpl(drivingService: MongoDatabase) : Repository {
    private val drivingSlots = drivingService.getCollection<DrivingSlot>("DrivingSlot")

    override fun createDrivingSlot(newDrivingSlot: DrivingSlot): String? {
        return newDrivingSlot.apply { drivingSlots.insertOne(newDrivingSlot) }._id
    }

    override fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): List<DrivingSlot> {
        return if (docs.instructorId == null) {
            drivingSlots.find(DrivingSlot::date eq docs.date.toString()).toList()
        } else {
            drivingSlots.find(and(DrivingSlot::date eq docs.date.toString(), DrivingSlot::instructorId eq docs.instructorId)).toList()
        }
    }

    override fun getFutureDrivingSlots(): List<DrivingSlot> {
        return drivingSlots.find().filter { el -> LocalDate.parse(el.date) > LocalDate.now() }.toList()
    }
}
