package dsdms.dossier.database
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.examStatus.ExamStatus

interface Repository {
    fun createDossier(newDossier: Dossier): String?

    fun readDossierFromId(id: String): Dossier?

    fun readDossierFromCf(cf: String): List<Dossier>

    fun updateExamStatus(newStatus: ExamStatus?, id: String): UpdateResult

    fun deleteDossier(id: String): DeleteResult
}