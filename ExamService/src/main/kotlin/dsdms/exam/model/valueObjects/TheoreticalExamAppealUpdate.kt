package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class TheoreticalExamAppealUpdate(
    val dossierId: String,
    val date: String
)