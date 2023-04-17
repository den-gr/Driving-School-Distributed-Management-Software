package dsdms.dossier.database
import dsdms.dossier.businessModel.dossier.Dossier

interface Repository {
    fun createDossier(newDossier: Dossier): Int

    fun readDossierFromId(id: Int): Dossier

    fun readDossierFromCf(cf: String): List<Dossier>
}