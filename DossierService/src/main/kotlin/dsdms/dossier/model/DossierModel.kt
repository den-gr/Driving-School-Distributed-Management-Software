package dsdms.dossier.model

import com.mongodb.client.MongoDatabase
import dsdms.dossier.database.MongoDossier
import dsdms.dossier.database.Repository
import dsdms.dossier.model.examAttempts.PracticalExamAttemptsImpl
import dsdms.dossier.model.examStatus.ExamStatusImpl


class DossierModel(dossierServiceDb: MongoDatabase) {
    private val newRepo: Repository = MongoDossier(dossierServiceDb)
    private fun validateNewDossier(newDossier: Dossier): Boolean {
//        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(newDossier.fiscal_code)
//        return alreadyExistingDossiers.count { el -> el.validity } == 0
        return true
    }

    fun saveNewDossier(documents: SubscriberDocuments): String? {
        val dossier = Dossier(documents.name, documents.surname, documents.fiscal_code,
            true,
            examAttempts = PracticalExamAttemptsImpl(), examStatus = ExamStatusImpl()
        )
        return if (validateNewDossier(dossier)) {
            newRepo.createDossier(dossier)
        } else null
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