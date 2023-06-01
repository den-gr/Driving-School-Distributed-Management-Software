package dsdms.exam.model.domainServices

import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.DoctorApprovalEvent
import dsdms.exam.model.valueObjects.TheoreticalExamAppealUpdate

interface ExamService {
    suspend fun saveNewTheoreticalExamPass(documents: DoctorApprovalEvent): InsertTheoreticalExamPassResult
    suspend fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    suspend fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus
    suspend fun insertNewExamAppeal(newExamDay: TheoreticalExamAppeal): DomainResponseStatus
    suspend fun getNextExamAppeals(): NextTheoreticalExamAppeals
    suspend fun putDossierInExamAppeal(theoreticalExamAppealUpdate: TheoreticalExamAppealUpdate): DomainResponseStatus
}