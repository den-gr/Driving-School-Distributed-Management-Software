package dsdms.exam.database

import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamDay
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass

interface Repository {
    fun dossierAlreadyHasOnePass(dossierId: String): Boolean
    fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass
    fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus
    fun getFutureTheoreticalExamDays(): List<TheoreticalExamDay>
    fun insertTheoreticalExamDay(newExamDay: TheoreticalExamDay): RepositoryResponseStatus
}
