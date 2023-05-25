package dsdms.driving.model

import dsdms.driving.model.domainServices.DrivingService
import dsdms.driving.model.domainServices.PracticalExamDomainService

interface Model {
    val drivingService: DrivingService
    val practicalExamDomainService: PracticalExamDomainService
}
