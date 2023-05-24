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
     * Dossier id already has one valid theoretical exam pass
     */
    EXAM_PASS_ALREADY_AVAILABLE,

    /**
     * Delete result was not acknowledged-
     */
    DELETE_ERROR,

    /**
     * Not found theoretical exam passes with given dossier id
     */
    ID_NOT_FOUND
}
