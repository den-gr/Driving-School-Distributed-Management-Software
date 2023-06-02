package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.datetime.LocalDate

interface ProvisionalLicenseService {
    /**
     * Registers a new provisional license for a dossier id
     * Dossier service is being notified to update theoretical exam status to passed
     * @param provisionalLicense: info to create the provisional license for a dossier id
     * @return: DomainResponseStatus
     *  - PROVISIONAL_LICENSE_ALREADY_EXISTS
     *  - OK
     */
    suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus

    /**
     * Returns provisional license holder for a specific provisional license
     * @param dossierId
     * @return Provisional license holder for given dossier id, can be null if not found
     */
    suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?

    /**
     * Response if a provisional license is on a particular date
     * @param dossierId
     * @param date when provisional license validity is requested
     * @return DomainResponseStatus
     *  - OK
     *  - PROVISIONAL_LICENSE_NOT_VALID
     *  - ID_NOT_FOUND provisional license not found
     */
    suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus

    /**
     * todo
     */
    suspend fun incrementProvisionalLicenseFailures(dossierId: String): DomainResponseStatus
}
