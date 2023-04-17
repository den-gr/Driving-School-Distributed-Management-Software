package dsdms.dossier.businessModel.dossier

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