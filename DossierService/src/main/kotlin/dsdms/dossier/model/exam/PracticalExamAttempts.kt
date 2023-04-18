package dsdms.dossier.model.exam

import kotlinx.serialization.Serializable

@Serializable
sealed interface PracticalExamAttempts {
    var attempts: Int

    fun verifyAttempts(): Boolean
}