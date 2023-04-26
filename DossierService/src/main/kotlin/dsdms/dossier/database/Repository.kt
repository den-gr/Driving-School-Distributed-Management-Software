package dsdms.dossier.database
import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus

interface Repository {
    fun createDossier(newDossier: Dossier): String?

    fun readDossierFromId(id: String): Dossier?

    fun readDossierFromCf(cf: String): List<Dossier>

    fun updateExamStatus(newStatus: ExamStatus?, id: String): RepositoryResponseStatus

    fun deleteDossier(id: String): RepositoryResponseStatus
}