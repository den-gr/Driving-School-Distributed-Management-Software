package dsdms.dossier.model.domainServices.subscriberCheck

import dsdms.dossier.database.Repository
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * Some dossier constants.
 */
object SubscriberConstants {
    /**
     * Min age for a subscriber to be subscribed.
     */
    const val MIN_AGE: Int = 16
}

/**
 * Methods for checking constrains of subscriber data.
 */
interface SubscriberControls {
    /**
     * @see SubscriberDocuments
     * @see Repository
     * @param givenDocuments for a specific subscriber
     * @param repository to check if info exists into db
     * @return True if a dossier already exists for a specific fiscal code, false otherwise
     */
    suspend fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments, repository: Repository): Boolean

    /**
     * @param givenDocuments
     * @return true if subscriber does not meet minimum age requirement, false otherwise
     */
    suspend fun checkSubscriberBirthdate(givenDocuments: SubscriberDocuments): Boolean

    /**
     * @param string: one or multiple string to check if they are in reality numeric
     * @return true if even just one string is numeric, false otherwise
     */
    suspend fun isNumeric(vararg string: String): Boolean
}