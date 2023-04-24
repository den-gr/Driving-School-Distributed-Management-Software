package dsdms.dossier.database

import dsdms.dossier.model.Dossier

/**
 * The ID of the dossier is equal to the index of the actual list
 */
abstract class FakeDB : Repository {
    private var dossiers = listOf<Dossier>()

    override fun readDossierFromCf(cf: String): List<Dossier> {
        return dossiers.filter { el -> el.fiscal_code == cf}
    }
}