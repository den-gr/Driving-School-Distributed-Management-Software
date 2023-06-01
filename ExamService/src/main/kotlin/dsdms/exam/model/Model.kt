package dsdms.exam.model

import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ProvisionalLicenseService

/**
 * Representing different services
 */
interface Model {
    val examService: ExamService
    val provisionalLicenseService: ProvisionalLicenseService
}
