package dsdms.doctor.handlers

import dsdms.doctor.model.Model
import dsdms.doctor.model.domainServices.BookedDoctorSlots
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class RouteHandlersImpl(private val model: Model) : RouteHandlers {
    
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun bookDoctorVisit(routingContext: RoutingContext) {
        try {
            GlobalScope.launch {
                val insertResult = model.doctorService.saveDoctorSlot(Json.decodeFromString(routingContext.body().asString()))

                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus)).end(
                    insertResult.visitDate ?: insertResult.domainResponseStatus.toString())
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getBookedDoctorSlots(routingContext: RoutingContext) {
        try {
            GlobalScope.launch {
                val result: BookedDoctorSlots = model.doctorService
                    .getOccupiedDoctorSlots(Json.decodeFromString(routingContext.body().asString()))

                routingContext.response()
                    .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                    .end(result.doctorSlots ?: result.domainResponseStatus.toString())
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun deleteDoctorSlot(routingContext: RoutingContext) {
        GlobalScope.launch {
            val result = model.doctorService.deleteDoctorSlot(routingContext.request().getParam("dossierId").toString())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.toString())
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun saveDoctorResult(routingContext: RoutingContext) {
        GlobalScope.launch {
            val result = model.doctorService.saveDoctorResult(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(result)).end(result.toString())
        }
    }
}
