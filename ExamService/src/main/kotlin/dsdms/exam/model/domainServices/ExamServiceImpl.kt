package dsdms.exam.model.domainServices

import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.DoctorApprovalEvent
import dsdms.exam.model.valueObjects.TheoreticalExamAppealUpdate
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class InsertTheoreticalExamPassResult(
    val domainResponseStatus: DomainResponseStatus,
    val theoreticalExamPass: String? = null
)

data class NextTheoreticalExamAppeals(
    val domainResponseStatus: DomainResponseStatus,
    val examAppeals: String? = null)

class ExamServiceImpl(private val repository: Repository) : ExamService {
    private suspend fun verifyExamPass(documents: DoctorApprovalEvent): DomainResponseStatus {
        return if (repository.dossierAlreadyHasOnePass(documents.dossierId))
            DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
        else DomainResponseStatus.OK
    }

    private suspend fun createTheoreticalExamPass(documents: DoctorApprovalEvent): TheoreticalExamPass =
        repository.saveNewTheoreticalExamPass(
            TheoreticalExamPass(
                documents.dossierId,
                documents.date,
                LocalDate.parse(documents.date).plus(DatePeriod(months = 6)).toString()
            )
        )

    override suspend fun saveNewTheoreticalExamPass(documents: DoctorApprovalEvent): InsertTheoreticalExamPassResult {
        val verifyResult = verifyExamPass(documents)
        return if (verifyResult == DomainResponseStatus.OK)
            InsertTheoreticalExamPassResult(verifyResult, Json.encodeToString(createTheoreticalExamPass(documents)))
        else InsertTheoreticalExamPassResult(verifyResult)

    }

    override suspend fun readTheoreticalExamPass(dossierId: String): TheoreticalExamPass? {
        return repository.getTheoreticalExamPass(dossierId)
    }

    override suspend fun deleteTheoreticalExamPass(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteTheoreticalExamPass(dossierId))
    }

    override suspend fun insertNewExamAppeal(newExamDay: TheoreticalExamAppeal): DomainResponseStatus {
        return if (repository.getFutureTheoreticalExamAppeals().any { el -> el.date == newExamDay.date })
            DomainResponseStatus.DATE_ALREADY_IN
        else
            repositoryToDomainConversionTable.getDomainCode(repository.insertTheoreticalExamDay(newExamDay))
    }

    override suspend fun getNextExamAppeals(): NextTheoreticalExamAppeals {
        val examAppeals = repository.getFutureTheoreticalExamAppeals()
        return if (examAppeals.isEmpty())
            NextTheoreticalExamAppeals(DomainResponseStatus.NO_EXAM_APPEALS)
        else
            NextTheoreticalExamAppeals(DomainResponseStatus.OK, Json.encodeToString(ListSerializer(TheoreticalExamAppeal.serializer()), examAppeals))
    }

    override suspend fun putDossierInExamAppeal(theoreticalExamAppealUpdate: TheoreticalExamAppealUpdate): DomainResponseStatus {
        val examAppeal: TheoreticalExamAppeal? = repository.getFutureTheoreticalExamAppeals().find { el -> el.date == theoreticalExamAppealUpdate.date }

        return if (examAppeal == null)
            DomainResponseStatus.APPEAL_NOT_FOUND
        else {
            if (examAppeal.registeredDossiers.contains(theoreticalExamAppealUpdate.dossierId) ||
                repository.getFutureTheoreticalExamAppeals().any { el -> el.registeredDossiers.contains(theoreticalExamAppealUpdate.dossierId) })
                DomainResponseStatus.DOSSIER_ALREADY_PUT
            else if (examAppeal.registeredDossiers.count() < examAppeal.numberOfPlaces)
                repositoryToDomainConversionTable.getDomainCode(
                    repository.updateExamAppeal(
                        examAppeal.date,
                        examAppeal.registeredDossiers.plus(theoreticalExamAppealUpdate.dossierId)
                    ))
            else DomainResponseStatus.PLACES_FINISHED
        }
    }
}

