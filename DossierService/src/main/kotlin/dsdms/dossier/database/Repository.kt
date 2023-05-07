package dsdms.dossier.database
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus

interface Repository {
    suspend fun createDossier(newDossier: Dossier): String?

    suspend fun readDossierFromId(id: String): Dossier?

    suspend fun readDossierFromCf(cf: String): List<Dossier>

    suspend fun updateExamStatus(newStatus: ExamStatus?, id: String): RepositoryResponseStatus

    suspend fun deleteDossier(id: String): RepositoryResponseStatus
}
