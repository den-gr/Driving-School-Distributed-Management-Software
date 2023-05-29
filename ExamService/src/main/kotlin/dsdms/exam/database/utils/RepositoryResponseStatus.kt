package dsdms.exam.database.utils

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
    PASS_NOT_FOUND_FOR_ID,

    /**
     * New exam day insert, was not acknowledged
     */
    INSERT_ERROR,

    /**
     * Update for list of dossiers into a theoretical exam appeal finished with some errors
     */
    UPDATE_ERROR
}
