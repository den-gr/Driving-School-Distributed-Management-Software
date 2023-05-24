package dsdms.doctor.model

import dsdms.doctor.database.Repository
import dsdms.doctor.model.domainServices.DoctorService
import dsdms.doctor.model.domainServices.DoctorServiceImpl
import io.vertx.ext.web.client.WebClient

class ModelImpl(repository: Repository, dossierServiceClient: WebClient, examServiceClient: WebClient) : Model {
    override val doctorService: DoctorService = DoctorServiceImpl(repository, dossierServiceClient, examServiceClient)
}
