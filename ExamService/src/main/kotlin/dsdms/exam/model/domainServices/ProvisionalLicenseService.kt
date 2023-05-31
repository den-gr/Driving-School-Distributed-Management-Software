package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface ProvisionalLicenseService {
    /**
     * PROVISIONAL_LICENSE_ALREADY_EXISTS
     * OK
     */
    fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus

    fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
