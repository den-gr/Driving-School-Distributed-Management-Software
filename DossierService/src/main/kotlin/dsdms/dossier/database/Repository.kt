package dsdms.dossier.database
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.entities.ExamStatusImpl
import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttempts

interface Repository {

    /**
     * @param newDossier to be inserted
     * @return id of the created dossier (could be null)
     */
    suspend fun createDossier(newDossier: Dossier): String?

    /**
     * @param id of a specific dossier
     * @return founded dossier (could be null)
     */
    suspend fun readDossierFromId(id: String): Dossier?

    /**
     * @param cf: fiscal code to read a dossier from
     * @return the list of dossiers valid and invalid with that fiscal code
     */
    suspend fun readDossierFromCf(cf: String): List<Dossier>

    /**
     * @param id of the dossier to be updated
     * @param newStatus for theoretical or pratical exam or both
     * @return Repository response status:
     *  - UPDATE_ERROR
     *  - OK
     */
    suspend fun updateExamStatus(newStatus: ExamStatusImpl?, id: String): RepositoryResponseStatus

    /**
     * @param id of the dossier to be updated
     * @return Repository response status:
     *  - DELETE_ERROR
     *  - OK
     */
    suspend fun deleteDossier(id: String): RepositoryResponseStatus

    /**
     * @param dossierId of the dossier to be updated
     * @return Repository response status:
     *  - UPDATE_ERROR
     *  - OK
     */
    suspend fun updateExamAttempts(dossierId: String, examAttempts: PracticalExamAttempts): RepositoryResponseStatus
}
