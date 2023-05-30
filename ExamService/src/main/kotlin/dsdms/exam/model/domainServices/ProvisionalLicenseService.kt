package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense

interface ProvisionalLicenseService {
    /**
     * PROVISIONAL_LICENSE_ALREADY_EXISTS
     * OK
     */
    fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus
}
