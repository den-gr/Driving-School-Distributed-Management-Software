package dsdms.doctor.database.utils

enum class RepositoryResponseStatus {
    OK,
    DELETE_ERROR,
    UPDATE_ERROR,
    MULTIPLE_EQUAL_IDS,
    NO_PROVISIONAL_LICENSE,
    INVALID_PROVISIONAL_LICENSE
}
