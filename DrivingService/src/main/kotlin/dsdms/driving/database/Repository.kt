package dsdms.driving.database

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate
import dsdms.driving.model.valueObjects.PracticalExamDay

interface Repository {

    /**
     * @param newDrivingSlot to be put into db
     * @return id (could be null) of inserted driving slot
     */
    suspend fun createDrivingSlot(newDrivingSlot: DrivingSlot): String?

    /**
     * @param docs: documents necessary to get occupied driving slots
     * @see DrivingSlotsRequest
     * @return list of booked driving slot (could be empty)
     */
    suspend fun getOccupiedDrivingSlots(docs: DrivingSlotsRequest): List<DrivingSlot>

    /**
     * @return all future booked driving slots, for each dossier id, instructor id and vehicle
     */
    suspend fun getFutureDrivingSlots(): List<DrivingSlot>

    /**
     * @param licensePlate representing a single vehicle
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
     * Registers a new practical exam day into db.
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
