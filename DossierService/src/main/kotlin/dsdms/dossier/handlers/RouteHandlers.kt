package dsdms.dossier.handlers

import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun handleDossierRegistration(routingContext: RoutingContext)

    fun handleDossierIdReading(routingContext: RoutingContext)

    fun handleDossierExamStatusUpdate(routingContext: RoutingContext)

    fun deleteDossier(routingContext: RoutingContext)

    fun setVerticle(vertx: Vertx)
}