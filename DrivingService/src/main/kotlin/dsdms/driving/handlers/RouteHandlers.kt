package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {

    fun registerNewDrivingSlot(routingContext: RoutingContext)
    fun getOccupiedDrivingSlots(routingContext: RoutingContext)
}
