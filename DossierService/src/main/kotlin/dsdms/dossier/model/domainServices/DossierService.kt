package dsdms.dossier.model.domainServices

import dsdms.dossier.database.Repository
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments

class DossierService(private val repository: Repository) {
    private suspend fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments): Boolean {
        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } == 0
    }

    suspend fun saveNewDossier(givenDocument: SubscriberDocuments): String? {
        return repository.createDossier(Dossier(givenDocument.name, givenDocument.surname, givenDocument.fiscal_code))
    }

    suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (checkDuplicatedFiscalCode(documents))
            DomainResponseStatus.OK
        else
            DomainResponseStatus.FISCAL_CODE_DUPLICATION
    }

    suspend fun readDossierFromId(id: String): Dossier? {
        return repository.readDossierFromId(id)
    }

    suspend fun updateExamStatus(data: ExamStatusUpdate, id: String): RepositoryResponseStatus {
        val newStatus = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical")
            newStatus?.modifyTheoretical(data.newStatus)
        else newStatus?.modifyPractical(data.newStatus)
        return repository.updateExamStatus(newStatus, id)
    }

    suspend fun deleteDossier(id: String): RepositoryResponseStatus {
        return repository.deleteDossier(id)
    }
}