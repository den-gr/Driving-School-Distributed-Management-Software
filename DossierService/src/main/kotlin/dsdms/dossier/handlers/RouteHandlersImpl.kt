package dsdms.dossier.handlers

import com.mongodb.client.MongoDatabase
import dsdms.dossier.service.DossierService
import dsdms.dossier.model.ExamStatusUpdate
import dsdms.dossier.model.SubscriberDocuments
import dsdms.dossier.service.Errors
import dsdms.dossier.service.conversionTable
import dsdms.dossier.service.getHttpCode
import java.net.HttpURLConnection.*
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class RouteHandlersImpl(dossierServiceDb: MongoDatabase) : RouteHandlers {
    private val dossierService: DossierService = DossierService(dossierServiceDb)
    override fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            val documents: SubscriberDocuments = Json.decodeFromString(routingContext.body().asString())

            val verifyResult = dossierService.verifyDocuments(documents)
            if (verifyResult == Errors.OK) {
                val id = dossierService.saveNewDossier(documents)
                routingContext.response().setStatusCode(conversionTable.getHttpCode(verifyResult)).end("ciao")
            } else {
                routingContext.response().setStatusCode(conversionTable.getHttpCode(verifyResult)).end(verifyResult.name)
            }
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun handleDossierIdReading(routingContext: RoutingContext) {
        val retrievedDossier = dossierService.readDossierFromId(routingContext.request().getParam("id").toString())
        if (retrievedDossier == null) {
            routingContext.response().setStatusCode(conversionTable.getHttpCode(Errors.ID_NOT_FOUND)).end(Errors.ID_NOT_FOUND.name)
        } else routingContext.response().setStatusCode(HTTP_OK).end(Json.encodeToString(retrievedDossier))
    }

    override fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
        val data: ExamStatusUpdate = Json.decodeFromString(routingContext.body().asString())
        val updateResult: Errors = dossierService.updateExamStatus(data, routingContext.request().getParam("id").toString())

        routingContext.response().setStatusCode(conversionTable.getHttpCode(updateResult)).end(updateResult.name)
    }

    override fun deleteDossier(routingContext: RoutingContext) {
        val deleteResult = dossierService.deleteDossier(routingContext.request().getParam("id").toString())
        routingContext.response().setStatusCode(conversionTable.getHttpCode(deleteResult)).end(deleteResult.name)
    }
}