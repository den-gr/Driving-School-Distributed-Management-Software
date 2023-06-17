/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

package dsdms.exam.database

import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

/**
 * Layer between domain and data storage.
 */
interface Repository {

    /**
     * @param dossierId
     * @return true if given dossier id already has one theoretical exam pass
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
     *  - ID_NOT_FOUND -> no theoretical exam passes were found for the given dossier id
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
    suspend fun saveProvisionalLicenseHolder(
        provisionalLicenseHolder: ProvisionalLicenseHolder,
    ): RepositoryResponseStatus

    /**
     * @see ProvisionalLicenseHolder
     * @return provisional license holder registered for the given dossier id, null otherwise
     */
    suspend fun findProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?

    /**
     * @param dossierId
     * @return Repository response status:
     *  - OK -> delete was successful
     *  - DELETE_ERROR -> deleting was not acknowledged
     *  - ID_NOT_FOUND -> no theoretical exam passes were found for the given dossier id
     */
    suspend fun deleteProvisionalLicenseHolder(dossierId: String): RepositoryResponseStatus

    /**
     * @param holder
     * @return Repository response status:
     *  - OK
     *  - UPDATE_ERROR -> update was not acknowledged
     */
    suspend fun updateProvisionalLicenseHolder(holder: ProvisionalLicenseHolder): RepositoryResponseStatus
}
