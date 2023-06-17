/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

package dsdms.driving.handlers

import dsdms.driving.model.Model
import dsdms.driving.model.entities.DrivingSlot
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
                model.drivingDomainService.saveNewDrivingSlot(Json.decodeFromString(routingContext.body().asString()))
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
                .drivingDomainService
                .getOccupiedDrivingSlots(parseHeaderValues(routingContext.queryParams()))

            val payload = if (result.drivingSlots != null) {
                Json.encodeToString(ListSerializer(DrivingSlot.serializer()), result.drivingSlots)
            } else {
                result.domainResponseStatus.name
            }
            routingContext
                .response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(payload)
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
            val result = model.drivingDomainService
                .deleteDrivingSlot(routingContext.request().getParam("id").toString())
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
