package dsdms.dossier.handlers

import dsdms.dossier.model.Model
import dsdms.dossier.model.domainServices.DomainResponseStatus
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection.HTTP_BAD_REQUEST

@OptIn(DelicateCoroutinesApi::class)
class RouteHandlersImpl(val model: Model) : RouteHandlers {
    override suspend fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            GlobalScope.launch {
                val insertResult = model.dossierService.saveNewDossier(Json.decodeFromString(routingContext.body().asString()))
                routingContext
                    .response()
                    .setStatusCode(domainConversionTable.getHttpCode(insertResult.domainResponseStatus))
                    .end(insertResult.dossierId ?: insertResult.domainResponseStatus.toString())
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override suspend fun handleDossierIdReading(routingContext: RoutingContext) {
        GlobalScope.launch {
            val result = model.dossierService.readDossierFromId(routingContext.request().getParam("id").toString())
            routingContext
                .response()
                .setStatusCode(domainConversionTable.getHttpCode(result.domainResponseStatus))
                .end(result.dossier ?: result.domainResponseStatus.toString())
        }
    }

    override suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
        try {
            val data: ExamStatusUpdate = Json.decodeFromString(routingContext.body().asString())
            GlobalScope.launch {
                val updateResult: DomainResponseStatus =
                    model.dossierService.updateExamStatus(data, routingContext.request().getParam("id").toString())
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(updateResult)).end(updateResult.name)
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override suspend fun handleDossierExamAttemptsUpdate(routingContext: RoutingContext) {
        GlobalScope.launch {
            val updateResult: DomainResponseStatus =
                model.dossierService.updateExamAttempts(routingContext.request().getParam("id").toString())
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(updateResult)).end(updateResult.name)
        }
    }
}
