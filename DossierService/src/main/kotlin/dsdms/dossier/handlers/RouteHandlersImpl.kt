package dsdms.dossier.handlers

import dsdms.dossier.model.DossierModel
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.SubscriberDocuments
import java.net.HttpURLConnection.*
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class RouteHandlersImpl : RouteHandlers {
    private val dossierModel: DossierModel = DossierModel()
    override fun handleDossierRegistration(routingContext: RoutingContext) {
        try {
            val documents: SubscriberDocuments = Json.decodeFromString(routingContext.body().asString())
            val id = dossierModel.saveNewDossier(documents)
            if (id != null) {
                routingContext.response().setStatusCode(HTTP_OK).end(id.toString())
            } else routingContext.response().setStatusCode(HTTP_CONFLICT).end("Duplicated info")
        } catch (ex: SerializationException) {
            routingContext.response().setStatusCode(HTTP_BAD_REQUEST).end(ex.message)
        }
    }

    override fun handleDossierIdReading(routingContext: RoutingContext) {
        val id = routingContext.request().getParam("id").toIntOrNull()
        val retrievedDossier: Dossier? = id?.let { dossierModel.readDossierFromId(it) }
        if (retrievedDossier == null) {
            routingContext.response().setStatusCode(HTTP_BAD_GATEWAY).end("Given Id is not present")
        } else routingContext.response().setStatusCode(HTTP_OK).end(Json.encodeToString(retrievedDossier))
    }

    override fun testHandler(routingContext: RoutingContext) {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dsdms")
        val col = database.getCollection<dsdms.dossier.Dossier>("Dossier")
        database.listCollectionNames().forEach { println(it) }
        var ris: String = ""
        col.find().forEach{ris += "$it \n"}
        routingContext.response().setStatusCode(HTTP_OK).end(ris)
    }
}