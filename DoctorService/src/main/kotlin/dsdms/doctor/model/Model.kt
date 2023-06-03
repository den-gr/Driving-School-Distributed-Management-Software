package dsdms.doctor.model

import dsdms.doctor.model.domainServices.DoctorService

/**
 * Main system model.
 */
interface Model {
    /**
     * Contains doctor visits domain logic.
     */
    val doctorService: DoctorService
}
