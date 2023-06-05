package dsdms.doctor.model

import dsdms.doctor.channels.ChannelsProvider
import dsdms.doctor.database.Repository
import dsdms.doctor.model.domainServices.DoctorDomainService
import dsdms.doctor.model.domainServices.DoctorDomainServiceImpl

/**
 * Main system model.
 * @param repository for connection with storage
 * @param channelsProvider for connection with other domain contexts
 */
class ModelImpl(repository: Repository, channelsProvider: ChannelsProvider) : Model {
    override val doctorDomainService: DoctorDomainService = DoctorDomainServiceImpl(repository, channelsProvider)
}
