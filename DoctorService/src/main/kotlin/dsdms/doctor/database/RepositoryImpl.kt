package dsdms.doctor.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class RepositoryImpl(doctorService: MongoDatabase) : Repository {
    private val doctorSlots = doctorService.getCollection<DoctorSlot>("DoctorSlot")
    override fun saveDoctorSlot(documents: DoctorSlot): String {
        return documents.apply { doctorSlots.insertOne(documents) }.date
    }

    override fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot> {
        return doctorSlots.find(DoctorSlot::date eq data.date).toList()
    }

    override fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus {
        return handleDeleteResult(
                doctorSlots.deleteOne(DoctorSlot::dossierId eq dossierId)
        )
    }

    private fun handleDeleteResult(deleteResult: DeleteResult): RepositoryResponseStatus {
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0) {
            RepositoryResponseStatus.DELETE_ERROR
        } else {
            RepositoryResponseStatus.OK
        }
    }
}
