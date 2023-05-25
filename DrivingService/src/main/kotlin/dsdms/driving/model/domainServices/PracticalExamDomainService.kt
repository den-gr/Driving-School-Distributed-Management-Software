package dsdms.driving.model.domainServices

import dsdms.driving.model.valueObjects.PracticalExamDay

data class PracticalExamDaysResponse(
    val status: DomainResponseStatus,
    val result: List<PracticalExamDay>? = null
)

/**
 * Manage practical exam day logic
 */
interface PracticalExamDomainService {
    /**
     * Set a day as a practical exam day
     * @param practicalExamDay exam day details
     * @return
     * - ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY if day is already set as practical exam day
     * - OK otherwise
     */
    suspend fun registerPracticalExamDay(practicalExamDay: PracticalExamDay): DomainResponseStatus

    /**
     * Get list of all practical exam days
     * @return an object with domain status code that always OK and a list with practical exam days
     */
    suspend fun getPracticalExamDays(): PracticalExamDaysResponse
}