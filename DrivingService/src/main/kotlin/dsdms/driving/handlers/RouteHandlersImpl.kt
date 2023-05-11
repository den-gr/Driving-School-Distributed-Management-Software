package dsdms.driving.handlers

import dsdms.driving.model.Model
import dsdms.driving.model.domainServices.DomainResponseStatus.NO_SLOT_OCCUPIED
import dsdms.driving.model.domainServices.DomainResponseStatus.OK
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class RouteHandlersImpl(val model: Model) : RouteHandlers {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun registerNewDrivingSlot(routingContext: RoutingContext) {
        try {
            val documents: DrivingSlotBooking = Json.decodeFromString(routingContext.body().asString())
            GlobalScope.launch {
                val verifyResult = model.drivingService.verifyDocuments(documents)
                if (verifyResult == OK) {
                    val id = model.drivingService.saveNewDrivingSlot(documents)
                    routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end(id)
                } else {
                    routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult))
                        .end(verifyResult.name)
                }
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getOccupiedDrivingSlots(routingContext: RoutingContext) {
        try {
            val data: GetDrivingSlotDocs = Json.decodeFromString(routingContext.body().asString())
            GlobalScope.launch {
                val occupiedDrivingSlots: List<DrivingSlot> = model.drivingService.getOccupiedDrivingSlots(data)
                if (occupiedDrivingSlots.isEmpty()) {
                    routingContext.response()
                        .setStatusCode(domainConversionTable.getHttpCode(NO_SLOT_OCCUPIED))
                        .end(NO_SLOT_OCCUPIED.name)
                } else {
                    routingContext.response()
                        .setStatusCode(domainConversionTable.getHttpCode(OK))
                        .end(Json.encodeToString(ListSerializer(DrivingSlot.serializer()), occupiedDrivingSlots))
                }
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }
}
