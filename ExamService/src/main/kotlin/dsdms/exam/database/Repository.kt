package dsdms.exam.database

import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface Repository {

    /**
     * @param dossierId
     * @return if given dossier id, already has one theoretical exam pass
     */
    suspend fun dossierAlreadyHasOnePass(dossierId: String): Boolean

    /**
     * @param theoreticalExamPass
     * @return the saved theoretical exam pass
     */
    suspend fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass

    /**
     * @param dossierId
     * @return the theoretical exam pass for the given dossier id, can be null if not found
     */
    suspend fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass?

    /**
     * @param dossierId
     * @return Repository response status:
     *  - OK -> delete was successful
     *  - DELETE_ERROR -> deleting was not acknowledged
     *  - PASS_NOT_FOUND_FOR_ID -> no theoretical exam passes were found for the given dossier id
     */
    suspend fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus

    /**
     * @return future (from today) theoretical exam appeals, list can be empty
     */
    suspend fun getFutureTheoreticalExamAppeals(): List<TheoreticalExamAppeal>

    /**
     * @param newExamDay
     * @return Repository response status:
     *  - OK
     *  - INSERT_ERROR -> insert operation was not acknowledged
     */
    suspend fun insertTheoreticalExamDay(newExamDay: TheoreticalExamAppeal): RepositoryResponseStatus

    /**
     * @param appealDate the date for the wanted theoretical exam appeal to be registered to
     * @param appealList the updated list of dossiers registered to the given appealDate
     * @return Repository response status:
     *  - OK
     *  - UPDATE_ERROR -> update was not acknowledged
     */
    suspend fun updateExamAppeal(appealDate: String, appealList: List<String>): RepositoryResponseStatus

    /**
     * @see ProvisionalLicenseHolder
     * @param provisionalLicenseHolder to be saved
     * @return Repository response status:
     *  - OK
     *  - INSERT_ERROR -> insert operation was not acknowledged
     */
    suspend fun saveProvisionalLicenseHolder(provisionalLicenseHolder: ProvisionalLicenseHolder): RepositoryResponseStatus

    /**
     * @see ProvisionalLicenseHolder
     * @return provisional license holder registered for the given dossier id, null otherwise
     */
    suspend fun findProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
