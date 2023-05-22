package dsdms.exam.model

import dsdms.exam.model.domainServices.ExamService

interface Model {
    val examService: ExamService
}
