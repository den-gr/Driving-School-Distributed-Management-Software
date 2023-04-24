package dsdms.dossier.service

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.UpdateResult
import dsdms.dossier.database.MongoDossier
import dsdms.dossier.database.Repository
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.ExamStatusUpdate
import dsdms.dossier.model.SubscriberDocuments

class DossierService(dossierServiceDb: MongoDatabase) {
    private val newRepo: Repository = MongoDossier(dossierServiceDb)
    private fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments): Boolean {
        val alreadyExistingDossiers: List<Dossier> = newRepo.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } == 0
    }

    fun saveNewDossier(givenDocument: SubscriberDocuments): String? {
        return newRepo.createDossier(Dossier(givenDocument.name, givenDocument.surname, givenDocument.fiscal_code))
    }

    fun verifyDocuments(documents: SubscriberDocuments): Errors {
        return if (checkDuplicatedFiscalCode(documents))
            Errors.OK
        else
            Errors.FISCAL_CODE_DUPLICATION
    }

    fun readDossierFromId(id: String): Dossier? {
        return newRepo.readDossierFromId(id)
    }

    fun updateExamStatus(data: ExamStatusUpdate, id: String): UpdateResult? {
        val dossier: Dossier? = readDossierFromId(id)
        return if (dossier == null) null
        else {
            val newStatus = dossier.examStatus
            if (data.exam == "theoretical")
                newStatus.modifyTheoretical(data.newStatus)
            else newStatus.modifyPractical(data.newStatus)
            newRepo.updateExamStatus(newStatus, id)
        }
    }

    fun deleteDossier(id: String): Errors {
        return if (this.readDossierFromId(id) != null) {
            if (newRepo.deleteDossier(id).wasAcknowledged()) {
                Errors.OK
            } else Errors.DELETE_ERROR
        } else Errors.ID_NOT_FOUND
    }
}