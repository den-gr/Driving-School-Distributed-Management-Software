package dsdms.driving.database

import com.mongodb.client.result.DeleteResult
import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.entities.Instructor
import dsdms.driving.model.entities.Vehicle
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate
import dsdms.driving.model.valueObjects.PracticalExamDay
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.lt

class RepositoryImpl(drivingServiceDB: CoroutineDatabase) : Repository {
    private val drivingSlots = drivingServiceDB.getCollection<DrivingSlot>("DrivingSlot")
    private val vehicles = drivingServiceDB.getCollection<Vehicle>("Vehicle")
    private val instructors = drivingServiceDB.getCollection<Instructor>("Instructor")
    private val practicalExamDays = drivingServiceDB.getCollection<PracticalExamDay>("practicalExamDays")

    override suspend fun createDrivingSlot(newDrivingSlot: DrivingSlot): String? {
        return newDrivingSlot.apply { drivingSlots.insertOne(newDrivingSlot) }._id
    }

    override suspend fun getOccupiedDrivingSlots(docs: DrivingSlotsRequest): List<DrivingSlot> {
        return if (docs.instructorId == null) {
            drivingSlots.find(DrivingSlot::date eq docs.date.toString()).toList()
        } else {
            drivingSlots.find(and(DrivingSlot::date eq docs.date.toString(), DrivingSlot::instructorId eq docs.instructorId)).toList()
        }
    }

    override suspend fun getFutureDrivingSlots(): List<DrivingSlot> {
        return drivingSlots.find().toList().filter { el -> LocalDate.parse(el.date) > now() }
    }

    override suspend fun countPastDrivingSlots(dossierId: String): Int{
        return drivingSlots.find(and(DrivingSlot::dossierId eq dossierId, DrivingSlot::date lt now().toString())).toList().count()
    }

    override suspend fun doesVehicleExist(licensePlate: LicensePlate): Boolean {
        return vehicles.find(Vehicle::licensePlate eq licensePlate).toList().isNotEmpty()
    }

    override suspend fun doesInstructorExist(instructorId: String): Boolean {
        return instructors.find(Instructor::_id eq instructorId).toList().isNotEmpty()
    }

    override suspend fun deleteDrivingSlot(drivingSlotId: String): RepositoryResponseStatus {
        return handleDeleteResult(
            runBlocking {
                drivingSlots.deleteOne(DrivingSlot::_id eq drivingSlotId)
            }
        )
    }

    override suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay) {
       practicalExamDays.insertOne(practicalExamDay)
    }

    override suspend fun getPracticalExamDays(): List<PracticalExamDay> {
        return practicalExamDays.find().toList()
    }

    private fun handleDeleteResult(deleteResult: DeleteResult): RepositoryResponseStatus {
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0) {
            RepositoryResponseStatus.DELETE_ERROR
        } else {
            RepositoryResponseStatus.OK
        }
    }

    private fun now(): LocalDate{
        return LocalDate.parse(java.time.LocalDate.now().toString())
    }
}
