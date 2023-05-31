package dsdms.exam.handlers.theoreticalExams

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface TheoreticalExamHandlers : Handlers {
    fun createTheoreticalExamPass(routingContext: RoutingContext)
    fun getTheoreticalExamPass(routingContext: RoutingContext)
    fun deleteTheoreticalExamPass(routingContext: RoutingContext)
    fun createNewTheoreticalExamAppeal(routingContext: RoutingContext)
    fun getNextTheoreticalExamAppeals(routingContext: RoutingContext)
    fun putDossierInExamAppeal(routingContext: RoutingContext)
}
