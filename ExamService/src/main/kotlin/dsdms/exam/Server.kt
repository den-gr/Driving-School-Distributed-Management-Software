package dsdms.exam

import com.mongodb.client.MongoDatabase
import dsdms.exam.database.Repository
import dsdms.exam.database.RepositoryImpl
import dsdms.exam.handlers.practicalHandlers.ProvisionalLicenseHandlers
import dsdms.exam.handlers.practicalHandlers.ProvisionalLicenseHandlersImpl
import dsdms.exam.handlers.theoreticalHandlers.TheoreticalExamHandlers
import dsdms.exam.handlers.theoreticalHandlers.TheoreticalExamHandlersImpl
import dsdms.exam.model.ModelImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import kotlin.system.exitProcess

class Server(private val port: Int, dbConnection: MongoDatabase) : AbstractVerticle() {

    private val repository: Repository = RepositoryImpl(dbConnection)
    private val provisionalLicenseHandlersImpl: ProvisionalLicenseHandlers = ProvisionalLicenseHandlersImpl(ModelImpl(repository))
    private val theoreticalExamHandlersImpl: TheoreticalExamHandlers = TheoreticalExamHandlersImpl(ModelImpl(repository))

    override fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        setRoutes(router)

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onFailure {
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
    }

    private fun setRoutes(router: Router) {
        router.get("/test").handler(this::testHandler)
        router.put("/theoreticalExam/pass").handler(theoreticalExamHandlersImpl::createTheoreticalExamPass)
        router.get("/theoreticalExam/pass/:id").handler(theoreticalExamHandlersImpl::getTheoreticalExamPass)
        router.delete("/theoreticalExam/pass/:id").handler(theoreticalExamHandlersImpl::deleteTheoreticalExamPass)

        router.post("/theoreticalExam/examDay").handler(theoreticalExamHandlersImpl::createNewTheoreticalExamDay)

        router.post("/provisionalLicences").handler(provisionalLicenseHandlersImpl::registerProvisionalLicence)
    }

    private fun testHandler(routingContext: RoutingContext) {
        routingContext.response().setStatusCode(200).end("999")
    }
}