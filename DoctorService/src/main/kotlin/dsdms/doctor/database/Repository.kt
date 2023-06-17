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

package dsdms.doctor.database

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import java.time.LocalDate

/**
 * Layer between domain and data storage.
 */
interface Repository {

    /**
     * @param doctorSlot: new doctor slot to be inserted
     * @return the date for which the new document has been inserted
     */
    suspend fun saveDoctorSlot(doctorSlot: DoctorSlot): String

    /**
     * @param date: the date when we want to obtain occupied doctor slots
     * @return lists of occupied driving slots in a certain date
     */
    suspend fun getOccupiedDoctorSlots(date: String): List<DoctorSlot>

    /**
     * @since each dossier can book only one doctor slot
     * @param dossierId: the id of a dossier
     * @return a repository response status: OK or DELETE_ERROR
     */
    suspend fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus

    /**
     * @param dossierId: the id of a specific dossier
     * @param today: optional parameters to take all doctor slots only after given date
     * @return a list of doctor slots booked by the dossierId (eventually filtered by date)
     */
    suspend fun getAllDoctorSlots(dossierId: String, today: LocalDate? = null): List<DoctorSlot>

    /**
     * @param document: doctor result for a specific dossier id
     * @return Repository response status: OK or INSERT_ERROR
     */
    suspend fun registerDoctorResult(document: DoctorResult): RepositoryResponseStatus
}
