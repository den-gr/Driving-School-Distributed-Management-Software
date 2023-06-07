package dsdms.driving.model

import dsdms.driving.channels.ChannelsProvider
import dsdms.driving.database.Repository
import dsdms.driving.model.domainServices.DrivingDomainService
import dsdms.driving.model.domainServices.DrivingDomainServiceImpl
import dsdms.driving.model.domainServices.PracticalExamDomainService
import dsdms.driving.model.domainServices.PracticalExamDomainServiceImpl

/**
 * Main system model.
 * @param repository for connection with storage
 * @param channels for connection with other domain contexts
 */
class ModelImpl(repository: Repository, channels: ChannelsProvider) : Model {
    override val drivingDomainService: DrivingDomainService = DrivingDomainServiceImpl(repository, channels)
    override val practicalExamDomainService: PracticalExamDomainService = PracticalExamDomainServiceImpl(repository)
}
