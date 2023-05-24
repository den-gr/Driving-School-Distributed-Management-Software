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
     * Not found any theoretical exam pass, for the given dossier id
     */
    PASS_NOT_FOUND_FOR_ID
}
