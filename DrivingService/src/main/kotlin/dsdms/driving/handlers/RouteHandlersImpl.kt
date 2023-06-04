package dsdms.driving.handlers

import dsdms.driving.model.Model
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.PracticalExamDay
import io.vertx.core.MultiMap
import io.vertx.ext.web.RoutingContext
import kotlinx.datetime.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

/**
 * @param model main system model.
 */
class RouteHandlersImpl(val model: Model) : RouteHandlers {

    override suspend fun registerNewDrivingSlot(routingContext: RoutingContext) {
        try {
            val insertResult =
                model.drivingService.saveNewDrivingSlot(Json.decodeFromString(routingContext.body().asString()))
            routingContext
                .response()
                .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus))
                .end(insertResult.drivingSlotId ?: insertResult.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getOccupiedDrivingSlots(routingContext: RoutingContext) {
        try {
            val result = model
                .drivingService
                .getOccupiedDrivingSlots(parseHeaderValues(routingContext.queryParams()))
            routingContext
                .response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(result.drivingSlots ?: result.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    private fun parseHeaderValues(headers: MultiMap): DrivingSlotsRequest {
        return DrivingSlotsRequest(
            LocalDate.parse(headers.get("date")),
            headers.get("instructorId"),
        )
    }

    override suspend fun deleteDrivingSlot(routingContext: RoutingContext) {
        try {
            val result = model.drivingService.deleteDrivingSlot(routingContext.request().getParam("id").toString())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun postPracticalExamDay(routingContext: RoutingContext) {
        try {
            val result = model.practicalExamDomainService
                .registerPracticalExamDay(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getPracticalExamDays(routingContext: RoutingContext) {
        try {
            val response = model.practicalExamDomainService.getPracticalExamDays()
            val result = Json.encodeToString(ListSerializer(PracticalExamDay.serializer()), response.result.orEmpty())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(response.status))
                .end(result)
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
