package dsdms.driving.model

import dsdms.driving.database.Repository
import dsdms.driving.model.domainServices.DrivingService
import dsdms.driving.model.domainServices.DrivingServiceImpl

class ModelImpl(repository: Repository) : Model {
    override val drivingService: DrivingService = DrivingServiceImpl(repository)
}
