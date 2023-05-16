package dsdms.doctor.handlers

import dsdms.doctor.model.Model
import dsdms.doctor.model.domainServices.DomainResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class RouteHandlersImpl(private val model: Model) : RouteHandlers {
    override fun bookDoctorVisit(routingContext: RoutingContext) {
        try {
            val documents: DoctorSlot = Json.decodeFromString(routingContext.body().asString())
            val verifyResult = model.doctorService.verifyDocuments(documents)
            if (verifyResult == DomainResponseStatus.OK) {
                val insertResult = model.doctorService.saveDoctorSlot(documents)
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end("Registered driving slot in: $insertResult")
            } else {
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult))
                    .end(verifyResult.name)
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun getBookedDoctorSlots(routingContext: RoutingContext) {
        try {
            val data: GetBookedDoctorSlots = Json.decodeFromString(routingContext.body().asString())
            val occupiedDoctorSlots: List<DoctorSlot> = model.doctorService.getOccupiedDoctorSlots(data)
            if (occupiedDoctorSlots.isEmpty()) {
                routingContext.response()
                    .setStatusCode(domainConversionTable.getHttpCode(DomainResponseStatus.NO_SLOT_OCCUPIED))
                    .end(DomainResponseStatus.NO_SLOT_OCCUPIED.name)
            } else {
                routingContext.response()
                    .setStatusCode(domainConversionTable.getHttpCode(DomainResponseStatus.OK))
                    .end(Json.encodeToString(ListSerializer(DoctorSlot.serializer()), occupiedDoctorSlots))
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun deleteDoctorSlot(routingContext: RoutingContext) {
        val result = model.doctorService.deleteDoctorSlot(routingContext.request().getParam("dossierId").toString())
        routingContext.response()
            .setStatusCode(domainConversionTable.getHttpCode(result))
            .end(result.toString())
    }
}
