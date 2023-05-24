package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ExamPassData

interface ExamService {
    fun verifyExamPass(documents: ExamPassData): DomainResponseStatus
    fun saveNewTheoreticalExamPass(documents: ExamPassData): TheoreticalExamPass
    fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
}