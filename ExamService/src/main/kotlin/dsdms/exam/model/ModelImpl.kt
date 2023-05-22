package dsdms.exam.model

import dsdms.exam.database.Repository
import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ExamServiceImpl

class ModelImpl(repository: Repository) : Model {
    override val examService: ExamService = ExamServiceImpl(repository)
}
