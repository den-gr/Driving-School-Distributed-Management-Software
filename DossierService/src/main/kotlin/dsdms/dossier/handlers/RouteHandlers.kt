package dsdms.dossier.handlers

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun handleDossierRegistration(routingContext: RoutingContext)

    fun handleDossierIdReading(routingContext: RoutingContext)

    fun testHandler(routingContext: RoutingContext)
}