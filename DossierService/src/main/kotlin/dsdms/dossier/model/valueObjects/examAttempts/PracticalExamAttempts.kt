package dsdms.dossier.model.valueObjects.examAttempts

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize(`as` = PracticalExamAttemptsImpl::class)
sealed interface PracticalExamAttempts {
    var attempts: Int
    fun verifyAttempts(): Boolean
}