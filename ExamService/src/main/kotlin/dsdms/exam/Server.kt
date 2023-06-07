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

/**
 * Vertx server.
 * @param port server port
 * @param dbConnection connection to database
 */
class Server(private val port: Int, dbConnection: CoroutineDatabase) : CoroutineVerticle() {

    private val repository: Repository = RepositoryImpl(dbConnection)

    override suspend fun start() {
        val channelsProvider = ChannelsProviderImpl(vertx)
        val model = ModelImpl(repository, channelsProvider)

        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        setRoutes(router, ProvisionalLicenseHandlersImpl(model), TheoreticalExamHandlersImpl(model))

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onFailure {
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
    }

    private fun setRoutes(
        router: Router,
        provisionalLicenseHandlers: ProvisionalLicenseHandlers,
        theoreticalExamHandlers: TheoreticalExamHandlers,
    ) {
        router.put("/theoreticalExam/pass")
            .coroutineHandler(theoreticalExamHandlers::createTheoreticalExamPass)
        router.get("/theoreticalExam/:id/pass")
            .coroutineHandler(theoreticalExamHandlers::getTheoreticalExamPass)
        router.delete("/theoreticalExam/:id/pass")
            .coroutineHandler(theoreticalExamHandlers::deleteTheoreticalExamPass)

        router.post("/theoreticalExam/examAppeal")
            .coroutineHandler(theoreticalExamHandlers::createNewTheoreticalExamAppeal)
        router.get("/theoreticalExam/examAppeal")
            .coroutineHandler(theoreticalExamHandlers::getNextTheoreticalExamAppeals)
        router.put("/theoreticalExam/examAppeal")
            .coroutineHandler(theoreticalExamHandlers::putDossierInExamAppeal)

        router.post("/provisionalLicences")
            .coroutineHandler(provisionalLicenseHandlers::registerProvisionalLicence)
        router.get("/provisionalLicences/:id")
            .coroutineHandler(provisionalLicenseHandlers::getProvisionalLicenseHolder)
        router.put("/provisionalLicences/:id")
            .coroutineHandler(provisionalLicenseHandlers::updateProvisionalLicenseHolder)
        router.get("/provisionalLicences/:id/validity")
            .coroutineHandler(provisionalLicenseHandlers::isProvisionalLicenseValidHandler)
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
