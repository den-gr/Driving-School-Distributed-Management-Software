package dsdms.exam.handlers.practicalHandlers

import io.vertx.ext.web.RoutingContext

interface ProvisionalLicenseHandlers {
    fun registerProvisionalLicence(routingContext: RoutingContext)
}
