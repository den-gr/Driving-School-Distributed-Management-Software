package dsdms.dossier.database
import dsdms.dossier.model.Dossier

interface Repository {
    fun createDossier(newDossier: Dossier): String?

    fun readDossierFromId(id: String): Dossier?

    fun readDossierFromCf(cf: String): List<Dossier>

    fun changeTheoreticalExamStatus(newStatus: Boolean, id: Int): Boolean?

    fun changePracticalExamStatus(newStatus: Boolean, id: Int): Boolean?
}