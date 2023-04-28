package dsdms.dossier.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
interface RouteHandlers {
    suspend fun handleDossierRegistration(routingContext: RoutingContext)

    suspend fun handleDossierIdReading(routingContext: RoutingContext)

    suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext)

    suspend fun deleteDossier(routingContext: RoutingContext)
}