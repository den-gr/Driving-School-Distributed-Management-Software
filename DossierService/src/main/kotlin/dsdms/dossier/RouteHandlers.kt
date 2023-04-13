package dsdms.dossier

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun handleDossierRegistration(routingContext: RoutingContext)

    fun handleDossierReading(routingContext: RoutingContext)
}