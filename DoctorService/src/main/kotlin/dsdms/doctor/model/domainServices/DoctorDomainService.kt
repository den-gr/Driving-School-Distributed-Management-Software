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

package dsdms.doctor.model.domainServices

import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult

/**
 * Doctor context operational domain logic.
 */
interface DoctorDomainService {

    /**
     * @param doctorSlot: the new doctor slot to be registered
     * @return the doctor slot date
     */
    suspend fun saveDoctorSlot(doctorSlot: DoctorSlot): InsertDoctorVisitResult

    /**
     * @param date: containing the date for which we took occupied doctor slots
     * @return domain response status and possible list of occupied doctor slots in a provided date
     */
    suspend fun getOccupiedDoctorSlots(date: String): BookedDoctorSlots

    /**
     * @param dossierId: id for which to take occupied driving slots
     * @return Domain response status: OK if delete was successful, DELETE_ERROR otherwise
     */
    suspend fun deleteDoctorSlot(dossierId: String): DomainResponseStatus

    /**
     * @param doctorResult
     * @return Domain Response Status:
     *  - OK if result was acknowledge
     *  - INSERT_ERROR otherwise
     */
    suspend fun saveDoctorResult(doctorResult: DoctorResult): DomainResponseStatus
}
