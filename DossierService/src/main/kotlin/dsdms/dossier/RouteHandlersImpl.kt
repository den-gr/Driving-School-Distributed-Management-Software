package dsdms.dossier

import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

@Serializable
data class MyDossier(val name: String, val surname: String, val fiscal_code: String)

class RouteHandlersImpl : RouteHandlers {
    override fun handleDossierRegistration(routingContext: RoutingContext) {
        val data = MyDossier("riccardo", "bacca", "BCCRCR99C07C573X")
        val string = Json.encodeToString(data)
        routingContext.response().setStatusCode(200).end(string)
    }

    override fun handleDossierReading(routingContext: RoutingContext) {

    }
}