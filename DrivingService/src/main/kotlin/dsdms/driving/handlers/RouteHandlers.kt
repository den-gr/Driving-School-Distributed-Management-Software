package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun getOccupiedDrivingSlots(routingContext: RoutingContext)
}
