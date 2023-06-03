package dsdms.dossier.database.utils

/**
 * Results of repository methods usage
 */
enum class RepositoryResponseStatus {
    /**
     * Request was successful.
     */
    OK,

    /**
     * Repository gets an error during delete operation.
     */
    DELETE_ERROR,

    /**
     * Repository gets an error during update operation.
     */
    UPDATE_ERROR
}
