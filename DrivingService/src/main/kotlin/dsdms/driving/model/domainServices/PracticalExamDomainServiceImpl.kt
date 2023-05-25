package dsdms.driving.model.domainServices

import dsdms.driving.database.Repository
import dsdms.driving.model.valueObjects.PracticalExamDay
import kotlinx.datetime.LocalDate


class PracticalExamDomainServiceImpl(private val repository: Repository): PracticalExamDomainService {
    override suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay): DomainResponseStatus {
        if(!isPracticalExamDay(practicalExamDay.date)){
            repository.registerPracticalExamDay(practicalExamDay)
            return DomainResponseStatus.OK
        }
        return DomainResponseStatus.ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY
    }

    override suspend fun getPracticalExamDays(): PracticalExamDaysResponse {
        return PracticalExamDaysResponse(DomainResponseStatus.OK, repository.getPracticalExamDays())
    }

    private suspend fun isPracticalExamDay(date: LocalDate): Boolean{
        return repository.getPracticalExamDays().map { elem -> elem.date }.contains(date)
    }
}