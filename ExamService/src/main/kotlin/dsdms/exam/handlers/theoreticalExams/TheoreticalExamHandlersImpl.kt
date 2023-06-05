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
            val insertResult = model.examService
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
            val retrievedTheoreticalExamPass: TheoreticalExamPass? = model.examService
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
            val deleteResult: DomainResponseStatus = model.examService
                .deleteTheoreticalExamPass(getDossierId(routingContext))
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(deleteResult)).end(deleteResult.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun createNewTheoreticalExamAppeal(routingContext: RoutingContext) {
        try {
            val insertResult: DomainResponseStatus = model.examService
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
            val result: NextTheoreticalExamAppeals = model.examService.getNextExamAppeals()
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(result.examAppeals ?: result.domainResponseStatus.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun putDossierInExamAppeal(routingContext: RoutingContext) {
        try {
            val result: DomainResponseStatus = model.examService
                .putDossierInExamAppeal(Json.decodeFromString(routingContext.body().asString()))
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(result)).end(result.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }
}
