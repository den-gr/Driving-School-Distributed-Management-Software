package dsdms.dossier.businessModel.dossier

interface PracticalExamAttempts {
    var attempts: Int

    fun verifyAttempts(): Boolean
}