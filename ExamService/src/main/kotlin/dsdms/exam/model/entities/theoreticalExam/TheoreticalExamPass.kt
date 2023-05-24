package dsdms.exam.model.entities.theoreticalExam

import kotlinx.serialization.Serializable

@Serializable
data class TheoreticalExamPass(
    val dossierId: String,
    val releaseDate: String,
    val expiryDate: String,
    val remainingAttempts: Int = 2
)