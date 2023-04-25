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

    fun updateExamStatus(data: ExamStatusUpdate, id: String): Errors {
        val newStatus = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical")
            newStatus?.modifyTheoretical(data.newStatus)
        else newStatus?.modifyPractical(data.newStatus)
        return handleUpdateResults(newRepo.updateExamStatus(newStatus, id))
    }

    private fun handleUpdateResults(updateResult: UpdateResult): Errors {
        return if (updateResult.matchedCount.toInt() != 1)
            Errors.MULTIPLE_EQUAL_IDS
        else if (updateResult.modifiedCount.toInt() != 1 || !updateResult.wasAcknowledged())
            Errors.UPDATE_ERROR
        else Errors.OK
    }

    fun deleteDossier(id: String): Errors {
        val deleteResult = newRepo.deleteDossier(id)
        return if (!deleteResult.wasAcknowledged() || deleteResult.deletedCount.toInt() == 0)
            Errors.DELETE_ERROR
        else
            Errors.OK
    }
}