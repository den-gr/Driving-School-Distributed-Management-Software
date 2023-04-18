package dsdms.dossier.model

import dsdms.dossier.database.FakeDB
import dsdms.dossier.database.Repository
import dsdms.dossier.model.exam.PracticalExamAttemptsImpl


class DossierModel {
    private val repository: Repository = FakeDB()
    private fun validateNewDossier(newDossier: Dossier): Boolean {
        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(newDossier.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } == 0
    }

    fun saveNewDossier(documents: SubscriberDocuments): Int? {
        val dossier = Dossier(documents.name, documents.surname, documents.fiscal_code,
            true, null,
            examAttempts = PracticalExamAttemptsImpl()
        )
        return if (validateNewDossier(dossier)) {
            repository.createDossier(dossier)
        } else null
    }

    fun readDossierFromId(id: Int): Dossier? {
        return repository.readDossierFromId(id)
    }
}