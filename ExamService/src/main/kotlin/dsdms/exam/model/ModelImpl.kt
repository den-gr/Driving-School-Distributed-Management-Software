package dsdms.exam.model

import dsdms.exam.channels.ChannelsProvider
import dsdms.exam.database.Repository
import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ExamServiceImpl
import dsdms.exam.model.domainServices.ProvisionalLicenseService
import dsdms.exam.model.domainServices.ProvisionalLicenseServiceImpl

/**
 * Main system model.
 * @param repository for connection with storage
 * @param channelsProvider for connection with other domain contexts
 */
class ModelImpl(repository: Repository, channelsProvider: ChannelsProvider) : Model {
    override val examService: ExamService = ExamServiceImpl(repository)
    override val provisionalLicenseService: ProvisionalLicenseService =
        ProvisionalLicenseServiceImpl(repository, channelsProvider)
}
