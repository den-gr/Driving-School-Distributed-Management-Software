package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
interface RouteHandlers {
    fun registerDrivingSlot(routingContext: RoutingContext)
}