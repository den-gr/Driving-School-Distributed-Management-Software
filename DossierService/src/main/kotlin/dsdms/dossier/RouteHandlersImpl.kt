package dsdms.dossier

import dsdms.dossier.businessModel.dossier.*
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

class RouteHandlersImpl : RouteHandlers {
    override fun handleDossierRegistration(routingContext: RoutingContext) {
        println(routingContext.body().asString())
        val documents: SubscriberDocuments = Json.decodeFromString(routingContext.body().asString())
        println("documents: $documents")
        val id = saveDossier(documents)
        println("id: $id")
        if (id != null) {
            routingContext.response().setStatusCode(200).end(id.toString())
        } else routingContext.response().statusCode = 404
    }

    override fun handleDossierReading(routingContext: RoutingContext) {

    }
}