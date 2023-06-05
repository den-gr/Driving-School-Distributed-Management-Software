package dsdms.doctor.model

import dsdms.doctor.model.domainServices.DoctorDomainService

/**
 * Main system model.
 */
interface Model {
    /**
     * Contains doctor visits domain logic.
     */
    val doctorDomainService: DoctorDomainService
}
