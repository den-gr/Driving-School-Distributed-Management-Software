package dsdms.exam.handlers.theoreticalHandlers

import io.vertx.ext.web.RoutingContext

interface TheoreticalExamHandlers {
    fun createTheoreticalExamPass(routingContext: RoutingContext)
    fun getTheoreticalExamPass(routingContext: RoutingContext)
}
