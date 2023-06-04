package dsdms.driving.model

import dsdms.driving.model.domainServices.DrivingService
import dsdms.driving.model.domainServices.PracticalExamDomainService

/**
 * Main system model.
 */
interface Model {
    /**
     * Responsible for driving slot booking domain logic.
     */
    val drivingService: DrivingService

    /**
     * Responsible for practical exam domain logic.
     */
    val practicalExamDomainService: PracticalExamDomainService
}
