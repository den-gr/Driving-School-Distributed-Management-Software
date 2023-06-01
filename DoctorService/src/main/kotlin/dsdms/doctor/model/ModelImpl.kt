package dsdms.doctor.model

import dsdms.doctor.channels.ChannelsProvider
import dsdms.doctor.database.Repository
import dsdms.doctor.model.domainServices.DoctorService
import dsdms.doctor.model.domainServices.DoctorServiceImpl

class ModelImpl(repository: Repository, channelsProvider: ChannelsProvider) : Model {
    override val doctorService: DoctorService = DoctorServiceImpl(repository, channelsProvider)
}
