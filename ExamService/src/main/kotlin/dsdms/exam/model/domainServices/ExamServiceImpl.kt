package dsdms.exam.model.domainServices

import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamDay
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ExamPassData
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class InsertTheoreticalExamPassResult(
    val domainResponseStatus: DomainResponseStatus,
    val theoreticalExamPass: String? = null
)

class ExamServiceImpl(private val repository: Repository) : ExamService {
    private fun verifyExamPass(documents: ExamPassData): DomainResponseStatus {
        return if (repository.dossierAlreadyHasOnePass(documents.dossierId))
            DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
        else DomainResponseStatus.OK
    }

    private fun createTheoreticalExamPass(documents: ExamPassData): TheoreticalExamPass =
        repository.saveNewTheoreticalExamPass(
            TheoreticalExamPass(
                documents.dossierId,
                documents.date,
                LocalDate.parse(documents.date).plus(DatePeriod(months = 6)).toString()
            )
        )

    override fun saveNewTheoreticalExamPass(documents: ExamPassData): InsertTheoreticalExamPassResult {
        val verifyResult = verifyExamPass(documents)
        return if (verifyResult == DomainResponseStatus.OK)
            InsertTheoreticalExamPassResult(verifyResult, Json.encodeToString(createTheoreticalExamPass(documents)))
        else InsertTheoreticalExamPassResult(verifyResult)

    }

    override fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass? {
        return repository.getTheoreticalExamPass(dossierId)
    }

    override fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteTheoreticalExamPass(dossierId))
    }

    override fun insertNewExamDay(newExamDay: TheoreticalExamDay): DomainResponseStatus {
        return if (repository.getFutureTheoreticalExamDays().any { el -> el.date == newExamDay.date })
            DomainResponseStatus.DATE_ALREADY_IN
        else
            repositoryToDomainConversionTable.getDomainCode(repository.insertTheoreticalExamDay(newExamDay))
    }
}

