package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamDay
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ExamPassData

interface ExamService {
    fun saveNewTheoreticalExamPass(documents: ExamPassData): InsertTheoreticalExamPassResult
    fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus
    fun insertNewExamDay(newExamDay: TheoreticalExamDay): DomainResponseStatus
}