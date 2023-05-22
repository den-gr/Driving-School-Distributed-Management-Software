package dsdms.dossier.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Represents documents that need to be passed to update exam status
 * @param exam: could be theoretical or practical
 * @param newStatus: could be true or false
 */
@Serializable
data class ExamStatusUpdate(
    val exam: String,
    val newStatus: Boolean
)