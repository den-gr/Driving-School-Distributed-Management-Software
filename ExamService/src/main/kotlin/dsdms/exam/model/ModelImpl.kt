package dsdms.exam.model

import dsdms.exam.channels.ChannelsProvider
import dsdms.exam.database.Repository
import dsdms.exam.model.domainServices.ExamDomainService
import dsdms.exam.model.domainServices.ExamDomainServiceImpl
import dsdms.exam.model.domainServices.ProvisionalLicenseDomainService
import dsdms.exam.model.domainServices.ProvisionalLicenseDomainServiceImpl

/**
 * Main system model.
 * @param repository for connection with storage
 * @param channelsProvider for connection with other domain contexts
 */
class ModelImpl(repository: Repository, channelsProvider: ChannelsProvider) : Model {
    override val examDomainService: ExamDomainService = ExamDomainServiceImpl(repository)
    override val provisionalLicenseDomainService: ProvisionalLicenseDomainService =
        ProvisionalLicenseDomainServiceImpl(repository, channelsProvider)
}
