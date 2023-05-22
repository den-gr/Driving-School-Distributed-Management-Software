package dsdms.dossier.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Represents documents passed to initially create a new dossier
 * fiscal code is implicitly considered as correct
 */
@Serializable
data class SubscriberDocuments(
    val name: String,
    val surname: String,
    val birthdate: LocalDate,
    val fiscal_code: String
)
