package dsdms.dossier.model.domainServices

import dsdms.dossier.database.Repository
import dsdms.dossier.handlers.getDomainCode
import dsdms.dossier.handlers.repositoryToDomainConversionTable
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControls
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControlsImpl
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttempts
import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttemptsImpl

class DossierServiceImpl(private val repository: Repository): DossierService {
    private val subscriberControls: SubscriberControls = SubscriberControlsImpl()

    override suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): String? {
        return repository.createDossier(Dossier(givenDocuments.name, givenDocuments.surname, givenDocuments.birthdate.toString(), givenDocuments.fiscal_code))
    }

    override suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (subscriberControls.checkDuplicatedFiscalCode(documents, repository))
            DomainResponseStatus.VALID_DOSSIER_ALREADY_EXISTS
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

    override suspend fun updateExamStatus(data: ExamStatusUpdate, id: String): DomainResponseStatus {
        val newStatus = readDossierFromId(id)?.examStatus
        if (data.exam == "theoretical") {
            newStatus?.modifyTheoretical(data.newStatus)
        } else {
            newStatus?.modifyPractical(data.newStatus)
        }
        return repositoryToDomainConversionTable.getDomainCode(repository.updateExamStatus(newStatus, id))
    }

    override suspend fun updateExamAttempts(dossierId: String): DomainResponseStatus {
        val dossier = readDossierFromId(dossierId)
        return if (dossier != null && dossier.examAttempts.verifyAttempts().not()) {
            DomainResponseStatus.MAX_ATTEMPTS_REACHED
        } else repositoryToDomainConversionTable.getDomainCode(repository.updateExamAttempts(dossierId, createExamAttempts(dossier)))
    }

    private fun createExamAttempts(dossier: Dossier?): PracticalExamAttempts {
        val examAttempts: PracticalExamAttempts = PracticalExamAttemptsImpl()
        if (dossier != null)
            examAttempts.attempts = dossier.examAttempts.attempts.plus(1)
        return examAttempts
    }
}
