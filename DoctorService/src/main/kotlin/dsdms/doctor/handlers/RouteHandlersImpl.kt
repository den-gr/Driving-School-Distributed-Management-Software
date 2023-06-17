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
                model.doctorDomainService.saveDoctorSlot(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus))
                .end(insertResult.visitDate ?: insertResult.domainResponseStatus.toString())
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getBookedDoctorSlots(routingContext: RoutingContext) {
        try {
            val result: BookedDoctorSlots = model.doctorDomainService
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
            val result = model.doctorDomainService
                .deleteDoctorSlot(routingContext.request().getParam("dossierId").toString())
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.toString())
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun saveDoctorResult(routingContext: RoutingContext) {
        try {
            val result = model.doctorDomainService
                .saveDoctorResult(Json.decodeFromString(routingContext.body().asString()))
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
