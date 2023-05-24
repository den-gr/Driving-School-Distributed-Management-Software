package dsdms.exam.model.domainServices

import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ExamPassData
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class ExamServiceImpl(private val repository: Repository) : ExamService {
    override fun verifyExamPass(documents: ExamPassData): DomainResponseStatus {
        return if (repository.dossierAlreadyHasOnePass(documents.dossierId))
            DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
        else DomainResponseStatus.OK
    }

    override fun saveNewTheoreticalExamPass(documents: ExamPassData): TheoreticalExamPass {
        return repository.saveNewTheoreticalExamPass(
            TheoreticalExamPass(
                documents.dossierId,
                documents.date,
                LocalDate.parse(documents.date).plus(DatePeriod(months = 6)).toString())
            )
    }

    override fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass? {
        return repository.getTheoreticalExamPass(dossierId)
    }

    override fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteTheoreticalExamPass(dossierId))
    }
}
