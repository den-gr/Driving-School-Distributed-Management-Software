package dsdms.exam.model.valueObjects

import dsdms.exam.model.entities.ProvisionalLicense
import kotlinx.serialization.Serializable

/**
 * Holder class for provisional license
 * @param provisionalLicense
 * @param practicalExamAttempts -> set to 0 by default, must be minor than MAX_PRACTICAL_EXAM_ATTEMPTS
 */
@Serializable
data class ProvisionalLicenseHolder(val practicalExamAttempts: Int = 0, val provisionalLicense: ProvisionalLicense) {
    companion object{
        private const val MAX_PRACTICAL_EXAM_ATTEMPTS = 3
    }

    init {
        checkPracticalExamAttempts()
    }

    fun registerPracticalExamFailure(): ProvisionalLicenseHolder{
        return this.copy(practicalExamAttempts = practicalExamAttempts + 1)
    }

    private fun checkPracticalExamAttempts(){
        if (practicalExamAttempts < 0 || practicalExamAttempts > MAX_PRACTICAL_EXAM_ATTEMPTS){
            throw IllegalStateException("Illegal number of practical exam attempts")
        }
    }
}

