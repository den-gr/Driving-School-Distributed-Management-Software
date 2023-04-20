package dsdms.dossier.handlers

import com.mongodb.client.MongoDatabase
import dsdms.dossier.model.DossierModel
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.SubscriberDocuments
import java.net.HttpURLConnection.*
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class RouteHandlersImpl(dossierServiceDb: MongoDatabase) : RouteHandlers {
    private val dossierModel: DossierModel = DossierModel(dossierServiceDb)
    override fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            val documents: SubscriberDocuments = Json.decodeFromString(routingContext.body().asString())
            val id = dossierModel.saveNewDossier(documents)
            if (id != null) {
                routingContext.response().setStatusCode(HTTP_OK).end(id)
            } else routingContext.response().setStatusCode(HTTP_CONFLICT).end("Server error !")
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun handleDossierIdReading(routingContext: RoutingContext) {
        val id = routingContext.request().getParam("id").toString()
        val retrievedDossier: Dossier? = dossierModel.readDossierFromId(id)
        if (retrievedDossier == null) {
            routingContext.response().setStatusCode(HTTP_BAD_GATEWAY).end("Given Id is not present")
        } else routingContext.response().setStatusCode(HTTP_OK).end(Json.encodeToString(retrievedDossier))
    }

    override fun handleDossierExamStatusUpdate(routingContext: RoutingContext) {
//        val id = routingContext.request().getParam("id").toIntOrNull()
//        val data: ExamStatusUpdate = Json.decodeFromString(routingContext.body().asString())
//        if (id?.let { dossierModel.readDossierFromId(it) } != null) {
//            val result = dossierModel.updateExamStatus(data, id)?.let { ExamStatusUpdate(data.exam, it) }
//            routingContext.response().setStatusCode(HTTP_OK).end(Json.encodeToString(result))
//        }
//        else routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end("Given Id is not present")
    }
}