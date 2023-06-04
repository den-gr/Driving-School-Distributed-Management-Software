package dsdms.driving.database.utils

import dsdms.driving.model.domainServices.DrivingService

/**
 * Types of response of the repository.
 */
enum class RepositoryResponseStatus {
    /**
     * Everything was ok.
     */
    OK,

    /**
     * Delete error unacknowledged.
     */
    DELETE_ERROR,

    /**
     * Given dossier id does not have a provisional license.
     * @see DrivingService
     */
    NO_PROVISIONAL_LICENSE,

    /**
     * Given dossier id has an invalid provisional license.
     * @see DrivingService
     */
    INVALID_PROVISIONAL_LICENSE,
}
