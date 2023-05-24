package dsdms.driving.handlers

import dsdms.driving.model.Model
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class RouteHandlersImpl(val model: Model) : RouteHandlers {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun registerNewDrivingSlot(routingContext: RoutingContext) {
        try {
            GlobalScope.launch {
                val insertResult = model.drivingService.saveNewDrivingSlot(Json.decodeFromString(routingContext.body().asString()))
                routingContext
                    .response()
                    .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus))
                    .end(insertResult.drivingSlotId ?: insertResult.domainResponseStatus.toString())
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getOccupiedDrivingSlots(routingContext: RoutingContext) {
        try {
            GlobalScope.launch {
                val result = model.drivingService.getOccupiedDrivingSlots(Json.decodeFromString(routingContext.body().asString()))
                routingContext
                    .response()
                    .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                    .end(result.drivingSlots ?: result.domainResponseStatus.toString())
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun deleteDrivingSlot(routingContext: RoutingContext) {
        GlobalScope.launch {
            val result = model.drivingService.deleteDrivingSlot(routingContext.request().getParam("id").toString())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.toString())
        }
    }
}
