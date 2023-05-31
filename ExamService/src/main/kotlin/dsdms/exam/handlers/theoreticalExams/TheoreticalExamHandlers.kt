package dsdms.exam.handlers.theoreticalExams

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface TheoreticalExamHandlers : Handlers {
    suspend fun createTheoreticalExamPass(routingContext: RoutingContext)
    suspend fun getTheoreticalExamPass(routingContext: RoutingContext)
    suspend fun deleteTheoreticalExamPass(routingContext: RoutingContext)
    suspend fun createNewTheoreticalExamAppeal(routingContext: RoutingContext)
    suspend fun getNextTheoreticalExamAppeals(routingContext: RoutingContext)
    suspend fun putDossierInExamAppeal(routingContext: RoutingContext)
}
