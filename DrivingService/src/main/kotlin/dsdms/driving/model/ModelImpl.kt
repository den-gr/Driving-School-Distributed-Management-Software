package dsdms.driving.model

import dsdms.driving.channels.ChannelsProvider
import dsdms.driving.database.Repository
import dsdms.driving.model.domainServices.DrivingService
import dsdms.driving.model.domainServices.DrivingServiceImpl
import dsdms.driving.model.domainServices.PracticalExamDomainService
import dsdms.driving.model.domainServices.PracticalExamDomainServiceImpl

class ModelImpl(repository: Repository, channels: ChannelsProvider) : Model {
    override val drivingService: DrivingService = DrivingServiceImpl(repository, channels)
    override val practicalExamDomainService: PracticalExamDomainService = PracticalExamDomainServiceImpl(repository)
}
