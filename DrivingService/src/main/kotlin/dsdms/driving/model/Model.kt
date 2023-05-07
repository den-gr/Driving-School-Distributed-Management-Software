package dsdms.driving.model

import dsdms.driving.model.domainServices.DrivingService

interface Model {
    val drivingService: DrivingService
}
