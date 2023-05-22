package dsdms.exam.database.utils

import dsdms.exam.model.domainServices.ExamService

enum class RepositoryResponseStatus {
    /**
     * Everything was ok
     */
    OK,

    /**
     * Delete error unacknowledged
     */
    DELETE_ERROR,

    /**
     * Given dossier id does not have a provisional license
     * @see ExamService
     */
    NO_PROVISIONAL_LICENSE,

    /**
     * Given dossier id has an invalid provisional license
     * @see ExamService
     */
    INVALID_PROVISIONAL_LICENSE
}
