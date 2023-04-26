package dsdms.dossier.model.domainServices

import dsdms.dossier.database.Repository
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments

class DossierService(private val repository: Repository) {
    private fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments): Boolean {
        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } == 0
    }

    fun saveNewDossier(givenDocument: SubscriberDocuments): String? {
        return repository.createDossier(Dossier(givenDocument.name, givenDocument.surname, givenDocument.fiscal_code))
    }

    fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (checkDuplicatedFiscalCode(documents))
            DomainResponseStatus.OK
        else
            DomainResponseStatus.FISCAL_CODE_DUPLICATION
    }

    fun readDossierFromId(id: String): Dossier? {
        return repository.readDossierFromId(id)
    }

    fun updateExamStatus(data: ExamStatusUpdate, id: String): RepositoryResponseStatus {
        val newStatus = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical")
            newStatus?.modifyTheoretical(data.newStatus)
        else newStatus?.modifyPractical(data.newStatus)
        return repository.updateExamStatus(newStatus, id)
    }

    fun deleteDossier(id: String): RepositoryResponseStatus {
        return repository.deleteDossier(id)
    }
}