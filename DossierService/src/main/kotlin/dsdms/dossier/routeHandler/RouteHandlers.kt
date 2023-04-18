package dsdms.dossier.routeHandler

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun handleDossierRegistration(routingContext: RoutingContext)

    fun handleDossierIdReading(routingContext: RoutingContext)
}