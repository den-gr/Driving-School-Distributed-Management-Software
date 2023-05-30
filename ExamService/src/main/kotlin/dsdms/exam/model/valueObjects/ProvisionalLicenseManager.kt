package dsdms.exam.model.valueObjects

import dsdms.exam.model.entities.ProvisionalLicense

data class ProvisionalLicenseManager(val numberPracticalExamAttempts: Int = 0, val provisionalLicense: ProvisionalLicense) {
    companion object{
        private const val MAX_PRACTICAL_EXAM_ATTEMPTS = 3
    }

    init {
        checkPracticalExamAttempts()
    }

    fun registerPracticalExamFailure(): ProvisionalLicenseManager{
        return this.copy(numberPracticalExamAttempts = numberPracticalExamAttempts + 1)
    }

    private fun checkPracticalExamAttempts(){
        if (numberPracticalExamAttempts < 0 || numberPracticalExamAttempts > MAX_PRACTICAL_EXAM_ATTEMPTS){
            throw IllegalStateException("Illegal number of practical exam attempts")
        }
    }
}

