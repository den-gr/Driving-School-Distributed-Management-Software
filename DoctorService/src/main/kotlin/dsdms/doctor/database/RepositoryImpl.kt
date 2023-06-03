package dsdms.doctor.database

import com.mongodb.client.result.DeleteResult
import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.time.LocalDate

class RepositoryImpl(doctorService: CoroutineDatabase) : Repository {
    private val doctorSlots = doctorService.getCollection<DoctorSlot>("DoctorSlot")
    private val doctorResults = doctorService.getCollection<DoctorResult>("DoctorResult")
    override suspend fun saveDoctorSlot(documents: DoctorSlot): String {
        return documents.apply { doctorSlots.insertOne(documents) }.date
    }

    override suspend fun getOccupiedDoctorSlots(date: String): List<DoctorSlot> {
        return doctorSlots.find(DoctorSlot::date eq date).toList()
    }

    override suspend fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus {
        return handleDeleteResult(doctorSlots.deleteOne(DoctorSlot::dossierId eq dossierId))
    }

    override suspend fun getAllDoctorSlots(dossierId: String, today: LocalDate?): List<DoctorSlot> {
        val result = doctorSlots.find(DoctorSlot::dossierId eq dossierId).toList()
        return if (today != null) {
            result.filter { el -> LocalDate.parse(el.date) >= today }
        } else {
            result
        }
    }

    override suspend fun registerDoctorResult(document: DoctorResult): RepositoryResponseStatus {
        return if (doctorResults.insertOne(document).wasAcknowledged()) {
            RepositoryResponseStatus.OK
        } else {
            RepositoryResponseStatus.INSERT_ERROR
        }
    }

    private fun handleDeleteResult(deleteResult: DeleteResult): RepositoryResponseStatus {
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0) {
            RepositoryResponseStatus.DELETE_ERROR
        } else {
            RepositoryResponseStatus.OK
        }
    }
}
