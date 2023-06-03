package dsdms.dossier.model.domainServices.subscriberCheck

import dsdms.dossier.database.Repository
import dsdms.dossier.model.valueObjects.SubscriberDocuments

interface SubscriberControls {

    /**
     * Min age for a subscriber to be subscribed
     */
    val MIN_AGE: Int
        get() = 16

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
     * @param repository
     * @return true if subscriber does not meet minimum age requirement, false otherwise
     */
    suspend fun checkSubscriberBirthdate(givenDocuments: SubscriberDocuments, repository: Repository): Boolean

    /**
     * @param string: one or multiple string to check if they are in reality numeric
     * @return true if even just one string is numeric, false otherwise
     */
    suspend fun isNumeric(vararg string: String): Boolean
}
