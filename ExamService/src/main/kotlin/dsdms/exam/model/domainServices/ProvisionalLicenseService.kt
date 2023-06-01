package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface ProvisionalLicenseService {
    /**
     * Registers a new provisional license for a dossier id
     * Dossier service is being notified to update theoretical exam status to passed
     * @param provisionalLicense: info to create the provisional license for a dossier id
     * @return:
     *  - PROVISIONAL_LICENSE_ALREADY_EXISTS
     *  - OK
     */
    suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus

    /**
     * Returns provisional license holder for a specific provisional license
     * @param dossierId
     * @return Provisional license holder for given dossier id, can be null
     */
    suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
