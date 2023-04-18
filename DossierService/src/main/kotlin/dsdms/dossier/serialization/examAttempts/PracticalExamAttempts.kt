package dsdms.dossier.serialization.examAttempts

import kotlinx.serialization.Serializable

@Serializable
sealed interface PracticalExamAttempts {
    var attempts: Int

    fun verifyAttempts(): Boolean
}