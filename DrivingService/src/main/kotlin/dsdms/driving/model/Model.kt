package dsdms.driving.model

import dsdms.driving.model.domainServices.DrivingDomainService
import dsdms.driving.model.domainServices.PracticalExamDomainService

/**
 * Main system model.
 */
interface Model {
    /**
     * Responsible for driving slot booking domain logic.
     */
    val drivingDomainService: DrivingDomainService

    /**
     * Responsible for practical exam domain logic.
     */
    val practicalExamDomainService: PracticalExamDomainService
}
