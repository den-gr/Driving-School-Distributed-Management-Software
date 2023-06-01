package dsdms.dossier.model.domainServices

import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControls

enum class DomainResponseStatus {
    /**
     * Otherwise...
     */
    OK,

    /**
     * While registering a new dossier, one is already present for a specific Fiscal code
     * @see DossierService
     */
    VALID_DOSSIER_ALREADY_EXISTS,

    /**
     * Given id to read a Dossier does not exist
     * @see DossierService
     */
    ID_NOT_FOUND,

    /**
     * Subscriber does not meet minimum age requirement to be registered
     * @see SubscriberControls
     */
    AGE_NOT_SUFFICIENT,

    /**
     * Given info about a subscriber are not the correct format
     * @see SubscriberControls
     */
    NAME_SURNAME_NOT_STRING,

    /**
     * Used when subscriber has already reached limit number of practical exam attempts (3)
     */
    MAX_ATTEMPTS_REACHED,

    DELETE_ERROR,
    UPDATE_ERROR,
    DOSSIER_INVALID
}
