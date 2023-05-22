package dsdms.exam.model.domainServices

import dsdms.exam.handlers.domainConversionTable

/**
 * @see domainConversionTable to identify HTTP codes, referred to enum values
 */
enum class DomainResponseStatus {
    /**
     * Otherwise...
     */
    OK,

    /**
     * While getting already booked slots, the given list was empty, we return this result
     */
    NO_SLOT_OCCUPIED,

    /**
     * In a specific date and time, the instructor wanted is not available to be booked
     */
    INSTRUCTOR_NOT_FREE,

    /**
     * In a specific date and time, the vehicle wanted is not available to be booked
     */
    VEHICLE_NOT_FREE,

    /**
     * Given dossier id has an invalid provisional license, booking can not be completed
     */
    INVALID_PROVISIONAL_LICENSE,

    /**
     * Given dossier id, while booking a driving slot, already has booked some driving slots
     */
    OCCUPIED_DRIVING_SLOTS,

    /**
     * Given info about vehicles or instructors, during driving slot booking are bad
     */
    BAD_VEHICLE_INSTRUCTOR_INFO,

    /**
     * Given dossier id while booking a driving slot does not have a provisional license (necessary)
     */
    NO_PROVISIONAL_LICENSE,

    /**
     * Delete result was not acknowledged-
     */
    DELETE_ERROR
}
