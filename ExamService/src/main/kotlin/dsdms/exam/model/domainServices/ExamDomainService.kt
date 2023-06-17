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

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.DoctorApprovalEvent
import dsdms.exam.model.valueObjects.TheoreticalExamAppealUpdate

/**
 * Implements domain logics to handle server routes.
 */
interface ExamDomainService {

    /**
     * Saves new theoretical exam pass, starting from given documents.
     * @see DoctorApprovalEvent
     * @param documents: ExamPassData
     * @return InsertTheoreticalExamPassResult, composed by:
     *  - domain response status: OK, EXAM_PASS_ALREADY_AVAILABLE
     *  - theoretical exam pass: null by default or if domain response status is not OK
     */
    suspend fun saveNewTheoreticalExamPass(documents: DoctorApprovalEvent): InsertTheoreticalExamPassResult

    /**
     * @param dossierId
     * @return theoretical exam pass if present, null otherwise
     */
    suspend fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass?

    /**
     * Deletes theoretical exam pass assigned to the given dossier id.
     * @param dossierId
     * @return: Domain Response status:
     *  - DELETE_ERROR -> some error is returned from repository
     *  - ID_NOT_FOUND -> no theoretical exam passes were found for given dossier id
     *  - OK otherwise
     */
    suspend fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus

    /**
     * Inserts a new exam appeal in the given date.
     * @see TheoreticalExamAppeal
     * @param newExamDay
     * @return Domain Response Status:
     *  - DATE_ALREADY_IN -> given date was already in
     *  - OK
     *  - INSERT_ERROR -> some error occurred from repository
     */
    suspend fun insertNewExamAppeal(newExamDay: TheoreticalExamAppeal): DomainResponseStatus

    /**
     * @return next theoretical exam appeals object, composed by:
     *  - Domain Response Status: OK or NO_EXAM_APPEALS
     *  - ExamAppeals, already encoded to string, null by default
     */
    suspend fun getNextExamAppeals(): NextTheoreticalExamAppeals

    /**
     * To put a given dossier id in exam appeal in the given date.
     * @see TheoreticalExamAppealUpdate
     * @return Domain Response Status:
     *  - APPEAL_NOT_FOUND in the given date
     *  - DOSSIER_ALREADY_PUT in some exam appeals
     *  - PLACES_FINISHED in the wanted exam appeal
     *  - UPDATE_ERROR -> some error occurred from repository
     *  - OK otherwise
     */
    suspend fun putDossierInExamAppeal(theoreticalExamAppealUpdate: TheoreticalExamAppealUpdate): DomainResponseStatus
}
