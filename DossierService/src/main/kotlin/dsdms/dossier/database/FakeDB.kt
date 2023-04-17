package dsdms.dossier.database

import dsdms.dossier.businessModel.dossier.Dossier

/**
 * The ID of the dossier is equal to the index of the actual list
 */
class FakeDB : Repository {
    private val dossiers = mutableListOf<Dossier>()

    override fun createDossier(newDossier: Dossier): Int {
        dossiers.add(newDossier)
        return dossiers.indexOf(newDossier)
    }

    override fun readDossierFromId(id: Int): Dossier {
        return dossiers[id]
    }

    override fun readDossierFromCf(cf: String): List<Dossier> {
        return dossiers.filter { el -> el.fiscal_code == cf}
    }
}