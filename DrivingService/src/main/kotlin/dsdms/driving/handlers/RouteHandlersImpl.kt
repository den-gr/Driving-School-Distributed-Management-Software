package dsdms.driving.handlers

import dsdms.driving.model.Model
import dsdms.driving.model.domainServices.DomainResponseStatus.NO_SLOT_OCCUPIED
import dsdms.driving.model.domainServices.DomainResponseStatus.OK
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class RouteHandlersImpl(val model: Model) : RouteHandlers {
    //    override fun registerDrivingSlot(routingContext: RoutingContext) {
//        try {
//            val documents: DrivingSlot = Json.decodeFromString(routingContext.body().asString())
//            val verifyResult = model.drivingService.verifyDrivingSlotDocuments(documents)
//            if (verifyResult == DomainResponseStatus.OK) {
//                val id = model.drivingService.saveNewDrivingSlot(documents)
//                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end(id)
//            } else {
//                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult))
//                    .end(verifyResult.name)
//            }
//        } catch (ex: SerializationException) {
//            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
//        }
//    }
    override fun getOccupiedDrivingSlots(routingContext: RoutingContext) {
        try {
            val data: GetDrivingSlotDocs = Json.decodeFromString(routingContext.body().asString())
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
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }
}
