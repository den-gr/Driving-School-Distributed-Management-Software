package dsdms.dossier.model

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.UpdateResult
import dsdms.dossier.database.MongoDossier
import dsdms.dossier.database.Repository
import dsdms.dossier.model.examStatus.ExamStatus
import java.net.HttpURLConnection.*


class DossierModel(dossierServiceDb: MongoDatabase) {
    private val newRepo: Repository = MongoDossier(dossierServiceDb)
    private fun validateNewDossier(givenDocuments: SubscriberDocuments): Boolean {
        val alreadyExistingDossiers: List<Dossier> = newRepo.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } == 0
    }

    fun saveNewDossier(givenDocument: SubscriberDocuments): String? {
        return newRepo.createDossier(Dossier(givenDocument.name, givenDocument.surname, givenDocument.fiscal_code))
    }

    fun verifyDocuments(documents: SubscriberDocuments): Int {
        return if (validateNewDossier(documents))
            HTTP_ACCEPTED
        else
            HTTP_CONFLICT
    }

    fun readDossierFromId(id: String): Dossier? {
        return newRepo.readDossierFromId(id)
    }

    fun updateExamStatus(data: ExamStatusUpdate, id: String): UpdateResult {
        val newStatus: ExamStatus? = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical")
            newStatus?.modifyTheoretical(data.newStatus)
        else newStatus?.modifyPractical(data.newStatus)
        return newRepo.updateExamStatus(newStatus, id)
    }
}