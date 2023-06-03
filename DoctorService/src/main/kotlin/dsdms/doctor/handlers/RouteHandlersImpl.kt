package dsdms.doctor.handlers

import dsdms.doctor.model.Model
import dsdms.doctor.model.domainServices.BookedDoctorSlots
import dsdms.doctor.model.domainServices.DomainResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

/**
 * @param model of the domain
 */
class RouteHandlersImpl(private val model: Model) : RouteHandlers {

    override suspend fun bookDoctorVisit(routingContext: RoutingContext) {
        try {
            val insertResult =
                model.doctorService.saveDoctorSlot(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus)).end(
                    insertResult.visitDate ?: insertResult.domainResponseStatus.toString(),
                )
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getBookedDoctorSlots(routingContext: RoutingContext) {
        try {
            val result: BookedDoctorSlots = model.doctorService
                .getOccupiedDoctorSlots(routingContext.request().getParam("date").toString())
            val payload = if (result.domainResponseStatus == DomainResponseStatus.OK) {
                Json.encodeToString(ListSerializer(DoctorSlot.serializer()), result.doctorSlots)
            } else {
                result.domainResponseStatus.name
            }
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(payload)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun deleteDoctorSlot(routingContext: RoutingContext) {
        try {
            val result = model.doctorService.deleteDoctorSlot(routingContext.request().getParam("dossierId").toString())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.toString())
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun saveDoctorResult(routingContext: RoutingContext) {
        try {
            val result = model.doctorService.saveDoctorResult(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(result)).end(result.toString())
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun handleException(ex: Exception, routingContext: RoutingContext) {
        println("Error message: ${ex.message}")
        when (ex) {
            is SerializationException, is MissingFieldException -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
            }
            else -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end(ex.message)
            }
        }
    }
}
