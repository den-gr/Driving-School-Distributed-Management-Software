package dsdms.exam.model

import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ProvisionalLicenseService

/**
 * Representing different services.
 */
interface Model {
    /**
     * Exam service domain logics.
     */
    val examService: ExamService

    /**
     * Provisional license service domain logics.
     */
    val provisionalLicenseService: ProvisionalLicenseService
}
