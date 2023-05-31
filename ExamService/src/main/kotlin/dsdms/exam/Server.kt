package dsdms.exam

import com.mongodb.client.MongoDatabase
import dsdms.exam.database.Repository
import dsdms.exam.database.RepositoryImpl
import dsdms.exam.handlers.provisionalLicense.ProvisionalLicenseHandlers
import dsdms.exam.handlers.provisionalLicense.ProvisionalLicenseHandlersImpl
import dsdms.exam.handlers.theoreticalExams.TheoreticalExamHandlers
import dsdms.exam.handlers.theoreticalExams.TheoreticalExamHandlersImpl
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
        router.get("/theoreticalExam/:id/pass").handler(theoreticalExamHandlersImpl::getTheoreticalExamPass)
        router.delete("/theoreticalExam/:id/pass").handler(theoreticalExamHandlersImpl::deleteTheoreticalExamPass)

        router.post("/theoreticalExam/examAppeal").handler(theoreticalExamHandlersImpl::createNewTheoreticalExamAppeal)
        router.get("/theoreticalExam/examAppeal").handler(theoreticalExamHandlersImpl::getNextTheoreticalExamAppeals)
        router.put("/theoreticalExam/examAppeal").handler(theoreticalExamHandlersImpl::putDossierInExamAppeal)

        router.post("/provisionalLicences").handler(provisionalLicenseHandlersImpl::registerProvisionalLicence)
        router.get("/provisionalLicences/:id").handler(provisionalLicenseHandlersImpl::getProvisionalLicenseHolder)
    }

    private fun testHandler(routingContext: RoutingContext) {
        routingContext.response().setStatusCode(200).end("999")
    }
}