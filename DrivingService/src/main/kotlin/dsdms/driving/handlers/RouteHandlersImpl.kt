package dsdms.driving.handlers

import dsdms.driving.model.Model
import dsdms.driving.model.domainServices.DomainResponseStatus
import dsdms.driving.model.entities.DrivingSlot
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection


@OptIn(DelicateCoroutinesApi::class)
class RouteHandlersImpl(val model: Model) : RouteHandlers {
    override fun registerDrivingSlot(routingContext: RoutingContext) {
        try {
            val documents: DrivingSlot = Json.decodeFromString(routingContext.body().asString())
            val verifyResult = model.drivingService.verifyDrivingSlotDocuments(documents)
            if (verifyResult == DomainResponseStatus.OK) {
                val id = model.drivingService.saveNewDrivingSlot(documents)
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end(id)
            } else {
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult))
                    .end(verifyResult.name)
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }
}