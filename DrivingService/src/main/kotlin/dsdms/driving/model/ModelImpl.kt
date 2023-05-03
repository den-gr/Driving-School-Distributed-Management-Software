package dsdms.driving.model


import dsdms.driving.database.Repository
import dsdms.driving.model.domainServices.DrivingService

class ModelImpl(repository: Repository) : Model {
    override val drivingService: DrivingService = DrivingService(repository)
}