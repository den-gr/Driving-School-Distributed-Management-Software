package dsdms.dossier.handlers

import dsdms.dossier.model.Model
import dsdms.dossier.model.domainServices.*
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import io.vertx.core.Vertx
import java.net.HttpURLConnection.*
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class RouteHandlersImpl(val model: Model) : RouteHandlers {
    private var vertx: Vertx? = null

    override fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            val documents: SubscriberDocuments = Json.decodeFromString(routingContext.body().asString())
            println("before external")

            val future = vertx?.executeBlocking{
                it.complete(model.dossierService.verifyDocuments(documents))
            }

            future?.onComplete{
                println("is completedd")
                val verifyResult = it.result()
                if (verifyResult == DomainResponseStatus.OK) {
                    val id = model.dossierService.saveNewDossier(documents)
                    routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end(id)
                } else {
                    routingContext.response().setStatusCode(domainConversionTable.getHttpCode(verifyResult)).end(verifyResult.name)
                }
            }
            println("after external")

        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun handleDossierIdReading(routingContext: RoutingContext) {
        val future = vertx?.executeBlocking {
            val dossierId = routingContext.request().getParam("id").toString()
            it.complete(model.dossierService.readDossierFromId(dossierId))
        }
        future?.onComplete{
            val retrievedDossier = it.result()
            if (retrievedDossier == null) {
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(DomainResponseStatus.ID_NOT_FOUND)).end(DomainResponseStatus.ID_NOT_FOUND.name)
            } else routingContext.response().setStatusCode(HTTP_OK).end(Json.encodeToString(retrievedDossier))
        }
    }

    override fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
        val data: ExamStatusUpdate = Json.decodeFromString(routingContext.body().asString())

        val future = vertx?.executeBlocking {
            val dossierId = routingContext.request().getParam("id").toString()
            it.complete(model.dossierService.updateExamStatus(data, dossierId))
        }
        future?.onComplete{
            val updateResult = it.result()
            routingContext.response().setStatusCode(dbConversionTable.getHttpCode(updateResult)).end(updateResult.name)
        }
    }

    override fun deleteDossier(routingContext: RoutingContext) {
        val future = vertx?.executeBlocking {
            val dossierId = routingContext.request().getParam("id").toString()
            it.complete(model.dossierService.deleteDossier(dossierId))
        }
        future?.onComplete{
            val deleteResult = it.result()
            routingContext.response().setStatusCode(dbConversionTable.getHttpCode(deleteResult)).end(deleteResult.name)
        }

    }

    override fun setVerticle(vertx: Vertx) {
        this.vertx = vertx
    }
}