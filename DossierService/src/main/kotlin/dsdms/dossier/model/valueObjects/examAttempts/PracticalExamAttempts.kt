package dsdms.dossier.model.valueObjects.examAttempts

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize(`as` = PracticalExamAttemptsImpl::class)
sealed interface PracticalExamAttempts {
    /**
     * Max number of attempts
     */
    var attempts: Int

    /**
     * @return if actual dossier passed or not limit number of attempts
     */
    fun verifyAttempts(): Boolean
}
