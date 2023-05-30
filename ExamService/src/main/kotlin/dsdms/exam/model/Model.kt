package dsdms.exam.model

import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ProvisionalLicenseService

interface Model {
    val examService: ExamService
    val provisionalLicenseService: ProvisionalLicenseService
}
