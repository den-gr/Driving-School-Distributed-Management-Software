package dsdms.dossier.handlers

import dsdms.dossier.model.Model
import dsdms.dossier.model.domainServices.DomainResponseStatus
import dsdms.dossier.model.valueObjects.ExamResultEvent

import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.net.HttpURLConnection

@OptIn(DelicateCoroutinesApi::class)
class RouteHandlersImpl(val model: Model) : RouteHandlers {
    val cjson = Json{
        encodeDefaults = true
    }

    override suspend fun handleDossierRegistration(routingContext: RoutingContext) {
        GlobalScope.launch {
            try {
                val insertResult =
                    model.dossierService.saveNewDossier(Json.decodeFromString(routingContext.body().asString()))
                routingContext
                    .response()
                    .setStatusCode(getHttpCode(insertResult.domainResponseStatus))
                    .end(insertResult.dossierId ?: insertResult.domainResponseStatus.name)

            } catch (ex: Exception) {
                handleException(ex, routingContext)
            }
        }
    }

    override suspend fun handleDossierIdReading(routingContext: RoutingContext) {
        GlobalScope.launch {
            val result = model.dossierService.readDossierFromId(routingContext.request().getParam("id").toString())
            val payload = if (result.domainResponseStatus == DomainResponseStatus.OK || result.domainResponseStatus == DomainResponseStatus.DOSSIER_INVALID)
                cjson.encodeToString(result.dossier)
            else result.domainResponseStatus.name
            routingContext.response()
                .putHeader("Content-Type", "application/json")
                .setStatusCode(getHttpCode(result.domainResponseStatus))
                .end(payload)
        }
    }

    override suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
        GlobalScope.launch {
            try {
                val data: ExamResultEvent = Json.decodeFromString(routingContext.body().asString())
                val updateResult: DomainResponseStatus =
                    model.dossierService.updateExamStatus(data, routingContext.request().getParam("id").toString())
                routingContext.response().setStatusCode(getHttpCode(updateResult))
                    .end(updateResult.name)
            } catch (ex: Exception) {
                handleException(ex, routingContext)
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun handleException(ex: Exception, routingContext: RoutingContext){
        println("Error message: ${ex.message}")
        when(ex){
            is SerializationException, is MissingFieldException -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
            }
            else ->{
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end(ex.message)
            }
        }
    }
}
