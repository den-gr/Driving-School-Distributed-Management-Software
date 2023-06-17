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

package dsdms.driving.database

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate
import dsdms.driving.model.valueObjects.PracticalExamDay

/**
 * Layer between domain and data storage.
 */
interface Repository {

    /**
     * @param newDrivingSlot to be put into db
     * @return id (could be null in case of errors) of inserted driving slot
     */
    suspend fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?

    /**
     * @param drivingSlotsRequest: documents necessary to get occupied driving slots
     * @see DrivingSlotsRequest
     * @return list of booked driving slot (could be empty in case of errors)
     */
    suspend fun getOccupiedDrivingSlots(drivingSlotsRequest: DrivingSlotsRequest): List<DrivingSlot>

    /**
     * @return all future booked driving slots, for each dossier id, instructor id and vehicle
     */
    suspend fun getFutureDrivingSlots(): List<DrivingSlot>

    /**
     * @param licensePlate representing id of a vehicle
     * @return:
     *  - true -> that license plate is valid and has been found
     *  - false -> otherwise
     */
    suspend fun doesVehicleExist(licensePlate: LicensePlate): Boolean

    /**
     * @param instructorId
     * @return:
     *  - true -> that instructor id is valid and has been found
     *  - false -> otherwise
     */
    suspend fun doesInstructorExist(instructorId: String): Boolean

    /**
     * @param drivingSlotId to be deleted
     * @return Repository Response Status:
     *  - DELETE_ERROR -> result was not acknowledged
     *  - OK -> otherwise
     */
    suspend fun deleteDrivingSlot(drivingSlotId: String): RepositoryResponseStatus

    /**
     * Registers a new practical exam day.
     */
    suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay)

    /**
     * @return list, eventually empty, containing all practical exam days
     */
    suspend fun getPracticalExamDays(): List<PracticalExamDay>

    /**
     * @since each dossier id to do an exam, have to be completed at least 12 driving slot
     * @param dossierId
     * @return number of past driving slots made by the given dossier id
     */
    suspend fun countPastDrivingSlots(dossierId: String): Int
}
