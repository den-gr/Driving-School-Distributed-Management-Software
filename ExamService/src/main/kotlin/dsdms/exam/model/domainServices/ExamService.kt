package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ExamPassData
import dsdms.exam.model.valueObjects.TheoreticalExamAppealUpdate

interface ExamService {
    fun saveNewTheoreticalExamPass(documents: ExamPassData): InsertTheoreticalExamPassResult
    fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus
    fun insertNewExamAppeal(newExamDay: TheoreticalExamAppeal): DomainResponseStatus
    fun getNextExamAppeals(): NextTheoreticalExamAppeals
    fun putDossierInExamAppeal(theoreticalExamAppealUpdate: TheoreticalExamAppealUpdate): DomainResponseStatus
}