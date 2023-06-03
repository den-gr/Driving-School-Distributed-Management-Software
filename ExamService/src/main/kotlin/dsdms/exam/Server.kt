package dsdms.exam

import dsdms.exam.channels.ChannelsProviderImpl
import dsdms.exam.database.Repository
import dsdms.exam.database.RepositoryImpl
import dsdms.exam.handlers.provisionalLicense.ProvisionalLicenseHandlers
import dsdms.exam.handlers.provisionalLicense.ProvisionalLicenseHandlersImpl
import dsdms.exam.handlers.theoreticalExams.TheoreticalExamHandlers
import dsdms.exam.handlers.theoreticalExams.TheoreticalExamHandlersImpl
import dsdms.exam.model.ModelImpl
import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.CoroutineDatabase
import kotlin.system.exitProcess

class Server(private val port: Int, dbConnection: CoroutineDatabase) : CoroutineVerticle() {

    private val repository: Repository = RepositoryImpl(dbConnection)
    private lateinit var provisionalLicenseHandlersImpl: ProvisionalLicenseHandlers
    private lateinit var theoreticalExamHandlersImpl: TheoreticalExamHandlers

    override suspend fun start() {
        val channelsProvider = ChannelsProviderImpl(vertx)
        val model = ModelImpl(repository, channelsProvider)
        provisionalLicenseHandlersImpl = ProvisionalLicenseHandlersImpl(model)
        theoreticalExamHandlersImpl = TheoreticalExamHandlersImpl(model)

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

        router.put("/theoreticalExam/pass").coroutineHandler(theoreticalExamHandlersImpl::createTheoreticalExamPass)
        router.get("/theoreticalExam/:id/pass").coroutineHandler(theoreticalExamHandlersImpl::getTheoreticalExamPass)
        router.delete("/theoreticalExam/:id/pass").coroutineHandler(theoreticalExamHandlersImpl::deleteTheoreticalExamPass)

        router.post("/theoreticalExam/examAppeal").coroutineHandler(theoreticalExamHandlersImpl::createNewTheoreticalExamAppeal)
        router.get("/theoreticalExam/examAppeal").coroutineHandler(theoreticalExamHandlersImpl::getNextTheoreticalExamAppeals)
        router.put("/theoreticalExam/examAppeal").coroutineHandler(theoreticalExamHandlersImpl::putDossierInExamAppeal)

        router.post("/provisionalLicences").coroutineHandler(provisionalLicenseHandlersImpl::registerProvisionalLicence)
        router.get("/provisionalLicences/:id").coroutineHandler(provisionalLicenseHandlersImpl::getProvisionalLicenseHolder)
        router.put("/provisionalLicences/:id").coroutineHandler(provisionalLicenseHandlersImpl::updateProvisionalLicenseHolder)
        router.get("/provisionalLicences/:id/validity").coroutineHandler(provisionalLicenseHandlersImpl::isProvisionalLicenseValidHandler)
    }

    private fun testHandler(routingContext: RoutingContext) {
        routingContext.response().setStatusCode(200).end("999")
    }

    private fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
        handler { ctx ->
            launch(Vertx.currentContext().dispatcher()) {
                try {
                    fn(ctx)
                } catch (e: Exception) {
                    ctx.fail(e)
                }
            }
        }
    }
}
