package dsdms.driving.model.domainServices

import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.DrivingSlotsRequest


interface DrivingService {

    /**
     * @param documents identifying the wanted driving slot to be booked
     * @return the Id of the created driving slot (could be null)
     * @return Domain Response Status:
     *  - OK -> all was fine
     *  - INSTRUCTOR_NOT_FREE -> wanted instructor in specific wanted day and time is not free
     *  - OCCUPIED_DRIVING_SLOTS ->that specific dossier has already booked one driving slot
     *  - VEHICLE_NOT_FREE -> wanted instructor in specific wanted day and time is not free
     *  - BAD_VEHICLE_INSTRUCTOR_INFO -> given vehicle or instructor does not exists
     */
    suspend fun saveNewDrivingSlot(documents: DrivingSlotBooking): DrivingSlotRegistrationResult

    /**
     * @return list of all booked driving slots
     * in a given date and optionally with a specific instructor Id (list could be empty)
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