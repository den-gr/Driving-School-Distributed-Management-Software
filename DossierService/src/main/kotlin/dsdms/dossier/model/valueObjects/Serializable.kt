package dsdms.dossier.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * fiscal code is implicitly considered as correct
 */
@Serializable
data class SubscriberDocuments(val name: String,
                               val surname: String, val fiscal_code: String)

@Serializable
data class ExamStatusUpdate(val exam: String,
                               val newStatus: Boolean)
