package dsdms.exam.model

import dsdms.exam.model.domainServices.ExamDomainService
import dsdms.exam.model.domainServices.ProvisionalLicenseDomainService

/**
 * Representing different services.
 */
interface Model {
    /**
     * Exam service domain logics.
     */
    val examDomainService: ExamDomainService

    /**
     * Provisional license service domain logics.
     */
    val provisionalLicenseDomainService: ProvisionalLicenseDomainService
}
