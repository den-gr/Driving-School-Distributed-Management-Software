package dsdms.exam.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import org.litote.kmongo.*
import java.time.LocalDate

class RepositoryImpl(examService: MongoDatabase) : Repository {
    private val theoreticalExamPassDb = examService.getCollection<TheoreticalExamPass>("TheoreticalExamPass")
    private val theoreticalExamAppeals = examService.getCollection<TheoreticalExamAppeal>("TheoreticalExamAppeal")
    private val provisionalLicenseHolders = examService.getCollection<ProvisionalLicenseHolder>("ProvisionalLicenseHolders")

    override fun dossierAlreadyHasOnePass(dossierId: String): Boolean {
        return theoreticalExamPassDb.find(TheoreticalExamPass::dossierId eq dossierId).toList().isNotEmpty()
    }

    override fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass {
        return theoreticalExamPass.apply { theoreticalExamPassDb.insertOne(theoreticalExamPass) }
    }

    override fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass? {
        return theoreticalExamPassDb.findOne(TheoreticalExamPass::dossierId eq dossierId)
    }

    override fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus {
        return handleDeleteResult(theoreticalExamPassDb.deleteOne(TheoreticalExamPass::dossierId eq dossierId))
    }

    override fun getFutureTheoreticalExamAppeals(): List<TheoreticalExamAppeal> {
        return theoreticalExamAppeals.find().toList().filter { el -> LocalDate.parse(el.date) > LocalDate.now() }
    }

    override fun insertTheoreticalExamDay(newExamDay: TheoreticalExamAppeal): RepositoryResponseStatus {
        return handleInsertResult(theoreticalExamAppeals.insertOne(newExamDay))
    }

    override fun updateExamAppeal(appealDate: String, appealList: List<String>): RepositoryResponseStatus {
        return handleUpdateResult(theoreticalExamAppeals
            .updateOne((TheoreticalExamAppeal::date eq appealDate), setValue(TheoreticalExamAppeal::registeredDossiers, appealList)))
    }

    override fun saveProvisionalLicenseHolder(provisionalLicenseHolder: ProvisionalLicenseHolder): RepositoryResponseStatus {
        return handleInsertResult(provisionalLicenseHolders.insertOne(provisionalLicenseHolder))
    }

    override fun findProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder? {
        return provisionalLicenseHolders.findOne {ProvisionalLicenseHolder::provisionalLicense / ProvisionalLicense::dossierId eq dossierId}
    }

    private fun handleUpdateResult(updateResult: UpdateResult): RepositoryResponseStatus {
        return if (updateResult.modifiedCount.toInt() != 1 || !updateResult.wasAcknowledged()) {
            RepositoryResponseStatus.UPDATE_ERROR
        } else {
            RepositoryResponseStatus.OK
        }
    }

    private fun handleInsertResult(insertOne: InsertOneResult): RepositoryResponseStatus {
        return if (insertOne.wasAcknowledged().not())
            RepositoryResponseStatus.INSERT_ERROR
        else RepositoryResponseStatus.OK
    }

    private fun handleDeleteResult(deleteOne: DeleteResult): RepositoryResponseStatus {
        return if (deleteOne.wasAcknowledged().not())
            RepositoryResponseStatus.DELETE_ERROR
        else if (deleteOne.deletedCount.toInt() == 0)
            RepositoryResponseStatus.PASS_NOT_FOUND_FOR_ID
        else RepositoryResponseStatus.OK
    }
}
