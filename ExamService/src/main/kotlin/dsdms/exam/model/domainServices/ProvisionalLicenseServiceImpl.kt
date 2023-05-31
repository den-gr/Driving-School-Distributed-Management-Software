package dsdms.exam.model.domainServices

import dsdms.exam.channels.ChannelsProvider
import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.Exam
import dsdms.exam.model.valueObjects.ExamOutcome
import dsdms.exam.model.valueObjects.ExamResultEvent
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

class ProvisionalLicenseServiceImpl(private val repository: Repository, private val channelsProvider: ChannelsProvider): ProvisionalLicenseService {
    override suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus {
        if(areThereAnotherProvisionalLicense(provisionalLicense.dossierId)){
            return DomainResponseStatus.PROVISIONAL_LICENSE_ALREADY_EXISTS
        }
        val eventNotificationResult = channelsProvider.dossierServiceChannel
            .updateExamStatus(provisionalLicense.dossierId, ExamResultEvent(Exam.THEORETICAL, ExamOutcome.PASSED))

        if(eventNotificationResult != DomainResponseStatus.OK){
            return eventNotificationResult
        }
        return repositoryToDomainConversionTable.getDomainCode(
            repository.saveProvisionalLicenseHolder(ProvisionalLicenseHolder(provisionalLicense = provisionalLicense)))
    }

    override suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder? {
        return repository.findProvisionalLicenseHolder(dossierId)
    }

    private suspend fun areThereAnotherProvisionalLicense(dossierId: String): Boolean{
        return repository.findProvisionalLicenseHolder(dossierId) != null
    }
}