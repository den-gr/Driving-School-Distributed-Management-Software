package dsdms.exam.model.domainServices

import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

class ProvisionalLicenseServiceImpl(private val repository: Repository): ProvisionalLicenseService {
    override fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus {
        if(areThereAnotherProvisionalLicense(provisionalLicense.dossierId)){
            return DomainResponseStatus.PROVISIONAL_LICENSE_ALREADY_EXISTS
        }
        return repositoryToDomainConversionTable.getDomainCode(
            repository.saveProvisionalLicenseHolder(ProvisionalLicenseHolder(provisionalLicense = provisionalLicense)))
    }

    override fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder? {
        return repository.findProvisionalLicenseHolder(dossierId)
    }

    private fun areThereAnotherProvisionalLicense(dossierId: String): Boolean{
        return repository.findProvisionalLicenseHolder(dossierId) != null
    }
}