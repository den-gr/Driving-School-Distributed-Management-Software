package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi

interface RouteHandlers {
    fun getOccupiedDrivingSlots(routingContext: RoutingContext)
}