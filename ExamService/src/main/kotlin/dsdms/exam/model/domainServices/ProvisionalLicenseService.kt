package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface ProvisionalLicenseService {
    /**
     * PROVISIONAL_LICENSE_ALREADY_EXISTS
     * OK
     */
    suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus

    suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
