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

package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.datetime.LocalDate

/**
 * Implements abstract provisional license logics.
 */
interface ProvisionalLicenseDomainService {
    /**
     * Registers a new provisional license for a dossier id
     * Dossier service is being notified to update theoretical exam status to PASSED.
     * @param provisionalLicense: info to create the provisional license for a dossier id
     * @return: DomainResponseStatus
     *  - PROVISIONAL_LICENSE_ALREADY_EXISTS
     *  - OK
     *  - EXAM_STATUS_UPDATE_ERROR
     */
    suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus

    /**
     * Returns provisional license holder for a specific provisional license.
     * @param dossierId
     * @return Provisional license holder for given dossier id, can be null if not found
     */
    suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?

    /**
     * Response if a provisional license is on a particular date.
     * @param dossierId
     * @param date when provisional license validity is requested
     * @return DomainResponseStatus
     *  - OK
     *  - PROVISIONAL_LICENSE_NOT_VALID
     *  - ID_NOT_FOUND provisional license not found
     */
    suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus

    /**
     * Register new failure of practical exam. If it arrives to 3 practical exam failure for this provisional license
     * the provisional license will be deleted and DossierContext will be notified
     * @param dossierId
     * @return DomainResponseStatus
     *  - OK
     *  - ID_NOT_FOUND provisional license not found
     *  - DELETE_ERROR
     *  - EXAM_STATUS_ERROR can not notify DossierContext about Provisional License invalidation
     */
    suspend fun incrementProvisionalLicenseFailures(dossierId: String): DomainResponseStatus

    /**
     * Register practical exam success for given dossier. As side effects:
     *  - Verify validity of dossier
     *  - Delete provisional license associated with dossier
     *  - Notify DossierContext about exam success
     * @param dossierId
     * @return DomainResponseStatus
     *  - OK
     *  - EXAM_STATUS_ERROR can not notify DossierContext about exam success
     *  - DOSSIER_NOT_VALID
     *  - DOSSIER_NOT_EXIST
     *  - DELETE_ERROR
     */
    suspend fun practicalExamSuccess(dossierId: String): DomainResponseStatus
}
