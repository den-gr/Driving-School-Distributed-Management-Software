package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class ExamPassData(
    val dossierId: String,
    val date: String
)