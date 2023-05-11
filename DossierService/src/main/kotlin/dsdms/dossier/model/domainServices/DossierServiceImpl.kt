package dsdms.dossier.model.domainServices

import dsdms.dossier.database.Repository
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.handlers.subscriberCheck.SubscriberControls
import dsdms.dossier.handlers.subscriberCheck.SubscriberControlsImpl
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments

class DossierServiceImpl(private val repository: Repository): DossierService {
    private val subscriberControls: SubscriberControls = SubscriberControlsImpl()

    override suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): String? {
        return repository.createDossier(Dossier(givenDocuments.name, givenDocuments.surname, givenDocuments.birthdate, givenDocuments.fiscal_code))
    }

    override suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (subscriberControls.checkDuplicatedFiscalCode(documents, repository))
            DomainResponseStatus.FISCAL_CODE_DUPLICATION
        else if (subscriberControls.checkSubscriberBirthdate(documents, repository))
            DomainResponseStatus.AGE_NOT_SUFFICIENT
        else if (subscriberControls.isNumeric(documents.name, documents.surname))
            DomainResponseStatus.NAME_SURNAME_NOT_STRING
        else
            DomainResponseStatus.OK
    }

    override suspend fun readDossierFromId(id: String): Dossier? {
        return repository.readDossierFromId(id)
    }

    override suspend fun updateExamStatus(data: ExamStatusUpdate, id: String): RepositoryResponseStatus {
        val newStatus = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical") {
            newStatus?.modifyTheoretical(data.newStatus)
        } else {
            newStatus?.modifyPractical(data.newStatus)
        }
        return repository.updateExamStatus(newStatus, id)
    }
}
