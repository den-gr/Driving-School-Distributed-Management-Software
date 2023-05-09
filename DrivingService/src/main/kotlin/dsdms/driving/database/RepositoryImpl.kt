package dsdms.driving.database

import com.mongodb.client.MongoDatabase
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.entities.Instructor
import dsdms.driving.model.entities.Vehicle
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import org.litote.kmongo.*
import java.time.LocalDate

class RepositoryImpl(drivingService: MongoDatabase) : Repository {
    private val drivingSlots = drivingService.getCollection<DrivingSlot>("DrivingSlot")
    private val vehicles = drivingService.getCollection<Vehicle>("Vehicle")
    private val instructors = drivingService.getCollection<Instructor>("Instructor")

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

    override fun doesVehicleExist(licensePlate: LicensePlate): Boolean {
        return vehicles.find(Vehicle::licensePlate eq licensePlate).toList().isNotEmpty()
    }

    override fun doesInstructorExist(instructorId: String): Boolean {
        return instructors.find(Instructor::_id eq instructorId).toList().isNotEmpty()
    }
}
