package dsdms.dossier.model.domainServices

import dsdms.dossier.database.Repository
import dsdms.dossier.handlers.getDomainCode
import dsdms.dossier.handlers.repositoryToDomainConversionTable
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControls
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControlsImpl
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.*

data class SaveDossierResult(
    val domainResponseStatus: DomainResponseStatus,
    val dossierId: String? = null
)

data class GetDossierResult(
    val domainResponseStatus: DomainResponseStatus,
    val dossier: Dossier? = null)

class DossierServiceImpl(private val repository: Repository): DossierService {
    private val subscriberControls: SubscriberControls = SubscriberControlsImpl()

    override suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): SaveDossierResult {
        val verifyResult = verifyDocuments(givenDocuments)
        return if (verifyResult == DomainResponseStatus.OK)
            SaveDossierResult(verifyResult, createDossier(givenDocuments))
        else return SaveDossierResult(verifyResult)
    }

    private suspend fun createDossier(givenDocuments: SubscriberDocuments): String? =
        repository.createDossier(Dossier(givenDocuments.name, givenDocuments.surname, givenDocuments.birthdate.toString(), givenDocuments.fiscal_code))

    private suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (subscriberControls.checkDuplicatedFiscalCode(documents, repository))
            DomainResponseStatus.VALID_DOSSIER_ALREADY_EXISTS
        else if (subscriberControls.checkSubscriberBirthdate(documents, repository))
            DomainResponseStatus.AGE_NOT_SUFFICIENT
        else if (subscriberControls.isNumeric(documents.name, documents.surname))
            DomainResponseStatus.NAME_SURNAME_NOT_STRING
        else
            DomainResponseStatus.OK
    }

    override suspend fun readDossierFromId(id: String): GetDossierResult {
        val dossier: Dossier? = repository.readDossierFromId(id)
        return if (dossier == null)
            GetDossierResult(DomainResponseStatus.ID_NOT_FOUND)
        else
            GetDossierResult(DomainResponseStatus.OK, dossier)
    }

    override suspend fun updateExamStatus(result: ExamResult, id: String): DomainResponseStatus {
        val examsProgress = readDossierFromId(id).dossier?.examsStatus
        return try {
            val newExamState = getNewExamProgressState(result,examsProgress)
            repositoryToDomainConversionTable.getDomainCode(repository.updateExamStatus(newExamState, id))
        }catch (ex: IllegalStateException){
            DomainResponseStatus.UPDATE_ERROR
        }
    }

    private fun getNewExamProgressState(result: ExamResult, currentExamsStatus: ExamsStatus?):ExamsStatus?{
        return if (result.exam == Exam.THEORETICAL) {
            if(result.outcome == ExamOutcome.PASSED){
                currentExamsStatus?.registerTheoreticalExamPassed()
            }else{
                throw IllegalStateException("Theoretical exam fail is not manged")
            }
        } else if(result.exam == Exam.PRACTICAL && result.outcome == ExamOutcome.PASSED){
            currentExamsStatus?.registerPracticalExamPassed()
        }else{
            currentExamsStatus?.registerProvisionalLicenceInvalidation()
        }
    }

//    override suspend fun updateExamAttempts(dossierId: String): DomainResponseStatus {
//        val dossier = readDossierFromId(dossierId).dossier
//        return if (dossier != null && dossier.examAttempts.verifyAttempts().not()) {
//            DomainResponseStatus.MAX_ATTEMPTS_REACHED
//        } else repositoryToDomainConversionTable.getDomainCode(repository.updateExamAttempts(dossierId, createExamAttempts(dossier)))
//    }

//    private fun createExamAttempts(dossier: Dossier?): PracticalExamAttempts {
//        val examAttempts: PracticalExamAttempts = PracticalExamAttemptsImpl()
//        if (dossier != null)
//            examAttempts.attempts = dossier.examAttempts.attempts.plus(1)
//        return examAttempts
//    }
}
