package dsdms.dossier.businessModel.dossier

import dsdms.dossier.database.FakeDB
import dsdms.dossier.database.Repository
import kotlinx.serialization.Serializable

val repository: Repository = FakeDB()

/**
 * fiscal code is implicitly considered as correct
 */
@Serializable
data class Dossier(
    val name: String,
    val surname: String,
    val fiscal_code: String, val validity: Boolean,
    val id: Int?, val practicalExamAttempts: PracticalExamAttempts)

@Serializable
data class SubscriberDocuments(val name: String,
                               val surname: String, val fiscal_code: String)

fun validate(newDossier: Dossier): Boolean {
    val alreadyExistingDossiers = repository.readDossierFromCf(newDossier.fiscal_code)
    return alreadyExistingDossiers.count { el -> el.validity } == 0
}

fun saveDossier(documents: SubscriberDocuments): Int? {
    val dossier = Dossier(documents.name, documents.surname, documents.fiscal_code,
        true, null,
        practicalExamAttempts = PracticalExamAttemptsImpl())
    return if (validate(dossier)) {
        repository.createDossier(dossier)
    } else null
}


