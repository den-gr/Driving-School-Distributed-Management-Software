package dsdms.dossier.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.examStatus.ExamStatus
import org.litote.kmongo.*

class MongoDossier(dossierServiceDb: MongoDatabase): Repository {
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

    override fun updateExamStatus(newStatus: ExamStatus?, id: String): UpdateResult {
        return dossiers.updateOne((Dossier::_id eq id), Dossier::examStatus.setTo(newStatus))
    }

    override fun deleteDossier(id: String): DeleteResult {
        return dossiers.deleteOne(Dossier::_id eq id)
    }
}