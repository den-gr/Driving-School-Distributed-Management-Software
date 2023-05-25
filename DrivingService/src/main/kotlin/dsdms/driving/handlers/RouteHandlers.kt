package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    suspend fun registerNewDrivingSlot(routingContext: RoutingContext)
    suspend fun getOccupiedDrivingSlots(routingContext: RoutingContext)
    suspend fun deleteDrivingSlot(routingContext: RoutingContext)
    suspend fun postPracticalExamDay(routingContext: RoutingContext)
    suspend fun getPracticalExamDays(routingContext: RoutingContext)
}
