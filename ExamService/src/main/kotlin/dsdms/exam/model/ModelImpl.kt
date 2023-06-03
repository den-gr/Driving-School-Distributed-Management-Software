package dsdms.exam.model

import dsdms.exam.channels.ChannelsProvider
import dsdms.exam.database.Repository
import dsdms.exam.model.domainServices.ExamService
import dsdms.exam.model.domainServices.ExamServiceImpl
import dsdms.exam.model.domainServices.ProvisionalLicenseService
import dsdms.exam.model.domainServices.ProvisionalLicenseServiceImpl

class ModelImpl(repository: Repository, channelProvider: ChannelsProvider) : Model {
    override val examService: ExamService = ExamServiceImpl(repository)
    override val provisionalLicenseService: ProvisionalLicenseService
        = ProvisionalLicenseServiceImpl(repository, channelProvider)
}
