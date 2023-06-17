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

package dsdms.dossier.handlers

import dsdms.dossier.model.Model
import dsdms.dossier.model.domainServices.DomainResponseStatus
import dsdms.dossier.model.valueObjects.ExamEvent
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

/**
 * @param model of the system
 */
class RouteHandlersImpl(val model: Model) : RouteHandlers {
    private val cjson = Json {
        encodeDefaults = true
    }

    override suspend fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            val insertResult =
                model.dossierDomainService.saveNewDossier(Json.decodeFromString(routingContext.body().asString()))
            routingContext
                .response()
                .setStatusCode(getHttpCode(insertResult.domainResponseStatus))
                .end(insertResult.dossierId ?: insertResult.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun handleDossierIdReading(routingContext: RoutingContext) {
        try {
            val result = model.dossierDomainService
                .readDossierFromId(routingContext.request().getParam("id").toString())
            val payload = if (result.domainResponseStatus == DomainResponseStatus.OK ||
                result.domainResponseStatus == DomainResponseStatus.DOSSIER_INVALID
            ) {
                cjson.encodeToString(result.dossier)
            } else {
                result.domainResponseStatus.name
            }
            routingContext.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(getHttpCode(result.domainResponseStatus))
                .end(payload)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
        try {
            val event: ExamEvent = ExamEvent.valueOf(routingContext.body().asString())
            val updateResult: DomainResponseStatus =
                model.dossierDomainService
                    .updateExamStatus(event, routingContext.request().getParam("id").toString())
            routingContext.response()
                .setStatusCode(getHttpCode(updateResult))
                .end(updateResult.name)
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
            else -> routingContext.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end(ex.message)
        }
    }
}
