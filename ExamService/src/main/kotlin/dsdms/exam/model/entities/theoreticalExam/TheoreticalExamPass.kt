package dsdms.exam.model.entities.theoreticalExam

import kotlinx.serialization.Serializable

/**
 * Represent theoretical exam pass for a dossier.
 * @param dossierId
 * @param releaseDate -> date in which the pass has been released
 * @param expiryDate -> date in which the pass expires
 * @param remainingAttempts -> set to 2 by default, not required
 */
@Serializable
data class TheoreticalExamPass(
    val dossierId: String,
    val releaseDate: String,
    val expiryDate: String,
    val remainingAttempts: Int = 2
)
