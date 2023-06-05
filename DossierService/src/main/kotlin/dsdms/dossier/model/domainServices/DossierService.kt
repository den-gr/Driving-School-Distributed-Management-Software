package dsdms.dossier.model.domainServices

import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamEvent
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * Representing dossier service model implementation.
 */
interface DossierService {

    /**
     * @see SubscriberDocuments
     * @param givenDocuments: to create and then save a new dossier referred to a specific subscriber
     * @since the insert could be faulty, the ID could be null
     * @return the dossier id and DomainResponseStatus:
     *  - OK
     *  - AGE_NOT_SUFFICIENT --> subscriber does not meet minimum 16 age
     *  - VALID_DOSSIER_ALREADY_EXIST --> for this subscriber (check by fiscal code)
     *  - NAME_SURNAME_NOT_STRING --> given data is faulty
     */
    suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): SaveDossierResult

    /**
     * @see Dossier
     * @param id of a specific dossier
     * @return Dossier (could be null if id does not exist)
     * - DOSSIER_INVALID
     * - OK
     */
    suspend fun readDossierFromId(id: String): GetDossierResult

    /**
     * @param event that notify what happens
     * @return RepositoryResponseStatus:
     * - UPDATE_ERROR --> some error occurred (result was not acknowledge)
     * - OK otherwise
     */
    suspend fun updateExamStatus(event: ExamEvent, id: String): DomainResponseStatus
}
