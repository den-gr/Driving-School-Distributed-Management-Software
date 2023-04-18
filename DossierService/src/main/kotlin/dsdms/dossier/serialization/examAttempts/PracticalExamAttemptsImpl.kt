package dsdms.dossier.serialization.examAttempts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Exam attempts")
class PracticalExamAttemptsImpl : PracticalExamAttempts {
    private val maxAttempts = 3
    override var attempts: Int = 0
        set(value) {
            if (verifyAttempts()) {
                field = value
            }
        }

    override fun verifyAttempts(): Boolean {
       return attempts < maxAttempts
    }
}