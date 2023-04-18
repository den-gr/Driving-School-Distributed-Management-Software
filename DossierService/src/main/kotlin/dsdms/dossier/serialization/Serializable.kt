package dsdms.dossier.serialization

import dsdms.dossier.serialization.examAttempts.PracticalExamAttemptsImpl
import kotlinx.serialization.Serializable

/**
 * fiscal code is implicitly considered as correct
 */
@Serializable
data class Dossier(
    val name: String,
    val surname: String,
    val fiscal_code: String, val validity: Boolean,
    val id: Int?, val examAttempts: PracticalExamAttemptsImpl
)

@Serializable
data class SubscriberDocuments(val name: String,
                               val surname: String, val fiscal_code: String)
