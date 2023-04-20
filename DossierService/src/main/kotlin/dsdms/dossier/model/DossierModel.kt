package dsdms.dossier.model

import com.mongodb.client.MongoDatabase
import dsdms.dossier.database.MongoDossier
import dsdms.dossier.database.Repository
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

    fun updateExamStatus(data: ExamStatusUpdate, id: Int) {
//        return if (data.exam == "theoretical")
//            this.repository.changeTheoreticalExamStatus(data.newStatus, id)
//        else
//            this.repository.changePracticalExamStatus(data.newStatus, id)
    }
}