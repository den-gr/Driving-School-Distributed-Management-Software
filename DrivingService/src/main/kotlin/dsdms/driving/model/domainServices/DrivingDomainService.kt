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
