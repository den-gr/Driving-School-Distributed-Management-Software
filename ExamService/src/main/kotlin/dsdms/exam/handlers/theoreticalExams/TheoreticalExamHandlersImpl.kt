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

package dsdms.exam.handlers.theoreticalExams

import dsdms.exam.handlers.domainConversionTable
import dsdms.exam.handlers.getHttpCode
import dsdms.exam.model.Model
import dsdms.exam.model.domainServices.DomainResponseStatus
import dsdms.exam.model.domainServices.NextTheoreticalExamAppeals
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

/**
 * @param model -> given model to theoretical exam handlers.
 */
class TheoreticalExamHandlersImpl(val model: Model) : TheoreticalExamHandlers {
    override suspend fun createTheoreticalExamPass(routingContext: RoutingContext) {
        try {
            val insertResult = model.examDomainService
                .saveNewTheoreticalExamPass(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus))
                .end(insertResult.theoreticalExamPass ?: insertResult.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getTheoreticalExamPass(routingContext: RoutingContext) {
        try {
            val retrievedTheoreticalExamPass: TheoreticalExamPass? = model.examDomainService
                .readTheoreticalExamPass(getDossierId(routingContext))
            if (retrievedTheoreticalExamPass == null) {
                routingContext.response()
                    .setStatusCode(domainConversionTable.getHttpCode(DomainResponseStatus.ID_NOT_FOUND))
                    .end(DomainResponseStatus.ID_NOT_FOUND.name)
            } else {
                routingContext.response()
                    .setStatusCode(HttpURLConnection.HTTP_OK)
                    .end(Json.encodeToString(retrievedTheoreticalExamPass))
            }
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun deleteTheoreticalExamPass(routingContext: RoutingContext) {
        try {
            val deleteResult: DomainResponseStatus = model.examDomainService
                .deleteTheoreticalExamPass(getDossierId(routingContext))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(deleteResult)).end(deleteResult.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun createNewTheoreticalExamAppeal(routingContext: RoutingContext) {
        try {
            val insertResult: DomainResponseStatus = model.examDomainService
                .insertNewExamAppeal(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(insertResult))
                .end(insertResult.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getNextTheoreticalExamAppeals(routingContext: RoutingContext) {
        try {
            val result: NextTheoreticalExamAppeals = model.examDomainService.getNextExamAppeals()
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(result.examAppeals ?: result.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun putDossierInExamAppeal(routingContext: RoutingContext) {
        try {
            val result: DomainResponseStatus = model.examDomainService
                .putDossierInExamAppeal(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(result)).end(result.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }
}
