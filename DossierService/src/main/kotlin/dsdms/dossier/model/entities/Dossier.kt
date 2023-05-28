package dsdms.dossier.model.entities

import dsdms.dossier.model.valueObjects.ExamsStatus
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents a complete Dossier representation
 * @param name: name of subscriber
 * @param surname: surname of subscriber
 * @param birthdate: birthdate of subscriber to verify that he has the minimum age
 * @param fiscal_code: of subscriber for additional info
 * @param validity: representing validity of the dossier (true by default)
 * @param examsStatus: representing practical and theoretical exams global states
 */
@Serializable
data class Dossier(
    val name: String,
    val surname: String,
    val birthdate: String,
    val fiscal_code: String,
    @Contextual val _id: String? = null,
    val validity: Boolean = true,
    val examsStatus: ExamsStatus = ExamsStatus()
)