package dsdms.exam.database

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass

interface Repository {
    fun dossierAlreadyHasOnePass(dossierId: String): Boolean
    fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass
    fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
}
