package dsdms.doctor.model

import dsdms.doctor.database.Repository
import dsdms.doctor.model.domainServices.DoctorService
import dsdms.doctor.model.domainServices.DoctorServiceImpl

class ModelImpl(repository: Repository) : Model {
    override val doctorService: DoctorService = DoctorServiceImpl(repository)
}
