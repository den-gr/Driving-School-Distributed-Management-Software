package dsdms.driving.model

import dsdms.driving.database.Repository
import dsdms.driving.model.domainServices.DrivingService
import dsdms.driving.model.domainServices.DrivingServiceImpl
import dsdms.driving.model.domainServices.PracticalExamDomainService
import dsdms.driving.model.domainServices.PracticalExamDomainServiceImpl

class ModelImpl(repository: Repository) : Model {
    override val drivingService: DrivingService = DrivingServiceImpl(repository)
    override val practicalExamDomainService: PracticalExamDomainService = PracticalExamDomainServiceImpl(repository)
}
