package dsdms.exam.handlers.provisionalLicense

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface ProvisionalLicenseHandlers : Handlers {
    suspend fun registerProvisionalLicence(routingContext: RoutingContext)
    suspend fun getProvisionalLicenseHolder(routingContext: RoutingContext)
}
