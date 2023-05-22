package dsdms.dossier.model.domainServices.subscriberCheck

import dsdms.dossier.database.Repository
import dsdms.dossier.model.valueObjects.SubscriberDocuments

interface SubscriberControls {
    val MIN_AGE: Int
        get() = 16

    suspend fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments, repository: Repository): Boolean

    suspend fun checkSubscriberBirthdate(givenDocuments: SubscriberDocuments, repository: Repository): Boolean

    suspend fun isNumeric(vararg string: String): Boolean
}