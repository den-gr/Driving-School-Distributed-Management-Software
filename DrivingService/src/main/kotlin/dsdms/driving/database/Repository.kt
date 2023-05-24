package dsdms.driving.database

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate

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
}
