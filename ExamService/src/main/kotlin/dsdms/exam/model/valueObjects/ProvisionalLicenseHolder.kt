package dsdms.exam.model.valueObjects

import dsdms.exam.model.entities.ProvisionalLicense
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * Holder class for provisional license
 * @param provisionalLicense
 * @param practicalExamAttempts -> set to 0 by default, must be minor than MAX_PRACTICAL_EXAM_ATTEMPTS
 */
@Serializable
data class ProvisionalLicenseHolder(val provisionalLicense: ProvisionalLicense, val practicalExamAttempts: Int = 0) {
    companion object{
        private const val MAX_PRACTICAL_EXAM_ATTEMPTS = 3
    }

    init {
        checkPracticalExamAttempts()
    }

    /**
     * Increment the number of practical exam failures
     * @return new ProvisionalLicenseHolder with incremented number of failures
     * @throws IllegalStateException if there are already max number of practical exam attempts
     */
    fun registerPracticalExamFailure(): ProvisionalLicenseHolder{
        return this.copy(practicalExamAttempts = practicalExamAttempts + 1)
    }

    /**
     * Is this provisional license valid in a particular day
     * @param date
     * @return true if provisional license is valid in indicated date
     */
    fun isValidOn(date: LocalDate): Boolean{
        return date in provisionalLicense.startValidity..provisionalLicense.endValidity
    }

    private fun checkPracticalExamAttempts(){
        if (practicalExamAttempts < 0 || practicalExamAttempts > MAX_PRACTICAL_EXAM_ATTEMPTS){
            throw IllegalStateException("Illegal number of practical exam attempts")
        }
    }
}

