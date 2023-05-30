package dsdms.exam.handlers.theoreticalHandlers

import io.vertx.ext.web.RoutingContext

interface TheoreticalExamHandlers {
    fun createTheoreticalExamPass(routingContext: RoutingContext)
    fun getTheoreticalExamPass(routingContext: RoutingContext)
    fun deleteTheoreticalExamPass(routingContext: RoutingContext)
    fun createNewTheoreticalExamAppeal(routingContext: RoutingContext)
    fun getNextTheoreticalExamAppeals(routingContext: RoutingContext)
    fun putDossierInExamAppeal(routingContext: RoutingContext)
}
