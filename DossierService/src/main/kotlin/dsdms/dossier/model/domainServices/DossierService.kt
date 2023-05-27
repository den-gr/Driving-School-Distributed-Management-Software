package dsdms.dossier.model.domainServices

import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamResult
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
     * @return DomainResponseStatus: <br/>
     *
     *      - OK --> no errors found
     *      - AGE_NOT_SUFFICIENT --> subscriber does not meet minimum 16 age
     *      - VALID_DOSSIER_ALREADY_EXIST --> for this subscriber (check by fiscal code)
     *      - NAME_SURNAME_NOT_STRING --> given data is faulty
     */
    suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): SaveDossierResult

    /**
     * @param id: for a specific dossier
     * @return Dossier (could be null if id does not exist)
     * @see Dossier
     */
    suspend fun readDossierFromId(id: String): GetDossierResult

    /**
     * @param result exam result that contains type and outcome of exam
     * @see ExamResult
     * @return RepositoryResponseStatus: <br/>
     *
     * - UPDATE_ERROR --> some error occurred (result was not acknowledge)
     * - OK otherwise
     */
    suspend fun updateExamStatus(result: ExamResult, id: String): DomainResponseStatus
}