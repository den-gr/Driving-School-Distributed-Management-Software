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

package dsdms.driving.model.domainServices

import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.DrivingSlotsRequest

/**
 * @param domainResponseStatus of driving slot registration request
 * @param drivingSlotId or null if there was some error
 */
data class DrivingSlotRegistrationResult(
    val domainResponseStatus: DomainResponseStatus,
    val drivingSlotId: String? = null,
)

/**
 * @param domainResponseStatus  of driving slots request
 * @param drivingSlots or null if there was some error
 */
data class DrivingSlotsRequestResult(
    val domainResponseStatus: DomainResponseStatus,
    val drivingSlots: List<DrivingSlot>? = null,
)

/**
 * Contains logic of driving slots managements.
 */
interface DrivingDomainService {

    /**
     * @param documents identifying the wanted driving slot to be booked
     * @return the ID of the created driving slot (could be null) and Domain Response Status:
     *  - OK -> all was fine
     *  - INSTRUCTOR_NOT_FREE -> wanted instructor in specific wanted day and time is not free
     *  - OCCUPIED_DRIVING_SLOTS -> that specific dossier has already booked one driving slot
     *  - VEHICLE_NOT_FREE -> wanted instructor in specific wanted day and time is not free
     *  - BAD_VEHICLE_INSTRUCTOR_INFO -> given vehicle or instructor does not exist
     *      If Driving slot is a practical exam:
     *  - NOT_ENOUGH_DRIVING_LESSONS_FOR_EXAM -> dossier has not done enough driving lessons to be able to book
     *      a practical exam
     *  - NOT_AN_EXAM_DAY -> the day must set as a practical exam day before booking an exam
     */
    suspend fun saveNewDrivingSlot(documents: DrivingSlotBooking): DrivingSlotRegistrationResult

    /**
     * @return list of all booked driving slots in a given date and optionally with a specific instructor ID
     * (list could be empty)
     * @param docs: GetDrivingSlotDocs
     * @see DrivingSlotsRequest
     */
    suspend fun getOccupiedDrivingSlots(docs: DrivingSlotsRequest): DrivingSlotsRequestResult

    /**
     * @param drivingSlotId: specific for the dossier to be deleted from repo
     * @return DomainResponseStatus:
     *  - DELETE_ERROR -> delete result was not acknowledged by the repository
     *  - OK otherwise
     */
    suspend fun deleteDrivingSlot(drivingSlotId: String): DomainResponseStatus
}
