package dsdms.doctor.model

import dsdms.doctor.model.domainServices.DoctorService

interface Model {
    val doctorService: DoctorService
}
