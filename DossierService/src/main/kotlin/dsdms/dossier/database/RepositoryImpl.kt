package dsdms.dossier.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus
import org.litote.kmongo.*

class RepositoryImpl(dossierServiceDb: MongoDatabase): Repository {
    private val dossiers = dossierServiceDb.getCollection<Dossier>("Dossier")

    override fun createDossier(newDossier: Dossier): String? {
        return newDossier.apply { dossiers.insertOne(newDossier) }._id
    }

    override fun readDossierFromId(id: String): Dossier? {
        return dossiers.findOneById(id)
    }

    override fun readDossierFromCf(cf: String): List<Dossier> {
        return dossiers.find(Dossier::fiscal_code eq cf).toList()
    }

    override fun updateExamStatus(newStatus: ExamStatus?, id: String): RepositoryResponseStatus {
        return handleUpdateResults(dossiers.updateOne((Dossier::_id eq id), Dossier::examStatus.setTo(newStatus)))
    }

    private fun handleUpdateResults(updateResult: UpdateResult): RepositoryResponseStatus {
        return if (updateResult.matchedCount.toInt() != 1)
            RepositoryResponseStatus.MULTIPLE_EQUAL_IDS
        else if (updateResult.modifiedCount.toInt() != 1 || !updateResult.wasAcknowledged())
            RepositoryResponseStatus.UPDATE_ERROR
        else RepositoryResponseStatus.OK
    }

    override fun deleteDossier(id: String): RepositoryResponseStatus {
        return handleDeleteResult(dossiers.deleteOne(Dossier::_id eq id))
    }

    private fun handleDeleteResult(deleteResult: DeleteResult): RepositoryResponseStatus {
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0)
            RepositoryResponseStatus.DELETE_ERROR
        else
            RepositoryResponseStatus.OK
    }
}