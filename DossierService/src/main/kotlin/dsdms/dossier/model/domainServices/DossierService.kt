package dsdms.dossier.model.domainServices

import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * Representing dossier service model implementation
 */
interface DossierService {

    /**
     * @see SubscriberDocuments
     * @param givenDocuments: to create and then save a new dossier referred to a specific subscriber
     * @return the id (Type: String) of the created dossier
     * @since the insert could be faulty, the ID could be null
     */
    suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): String?

    /**
     * @see SubscriberDocuments
     * @param documents: Subscriber documents to be verified before attempting to create and then insert the new dossier
     * @return DomainResponseStatus: <br/>
     *
     *      - OK --> no errors found
     *      - AGE_NOT_SUFFICIENT --> subscriber does not meet minimum 16 age
     *      - VALID_DOSSIER_ALREADY_EXIST --> for this subscriber (check by fiscal code)
     *      - NAME_SURNAME_NOT_STRING --> given data is faulty
     */
    suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus

    /**
     * @param id: for a specific dossier
     * @return Dossier (could be null if id does not exist)
     * @see Dossier
     */
    suspend fun readDossierFromId(id: String): Dossier?

    /**
     * @param data
     * @see ExamStatusUpdate
     * @return RepositoryResponseStatus: <br/>
     *
     * - UPDATE_ERROR --> some error occurred (result was not acknowledge)
     * - OK otherwise
     */
    suspend fun updateExamStatus(data: ExamStatusUpdate, id: String): DomainResponseStatus

    /**
     * Decreases practical exam attempts for this specific dossier id
     * @param dossierId
     * @return RepositoryResponseStatus: <br/>
     *
     * - UPDATE_ERROR --> some error occurred (result was not acknowledge)
     * - OK otherwise
     */
    suspend fun updateExamAttempts(dossierId: String): DomainResponseStatus
}