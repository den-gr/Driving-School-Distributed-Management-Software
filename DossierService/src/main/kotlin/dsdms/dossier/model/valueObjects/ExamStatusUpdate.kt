package dsdms.dossier.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class ExamStatusUpdate(
    val exam: String,
    val newStatus: Boolean
)