package dsdms.exam.handlers.provisionalLicense

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface ProvisionalLicenseHandlers : Handlers {
    fun registerProvisionalLicence(routingContext: RoutingContext)
    fun getProvisionalLicenseHolder(routingContext: RoutingContext)
}
