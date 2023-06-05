package dsdms.dossier

import dsdms.dossier.database.Repository
import dsdms.dossier.database.RepositoryImpl
import dsdms.dossier.handlers.RouteHandlers
import dsdms.dossier.handlers.RouteHandlersImpl
import dsdms.dossier.model.ModelImpl
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
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        setRoutes(router, RouteHandlersImpl(ModelImpl(repository)))

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onFailure {
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
    }

    private fun setRoutes(router: Router, handlersImpl: RouteHandlers) {
        router.post("/dossiers").coroutineHandler(handlersImpl::handleDossierRegistration)
        router.get("/dossiers/:id").coroutineHandler(handlersImpl::handleDossierIdReading)
        router.put("/dossiers/:id/examStatus").coroutineHandler(handlersImpl::handleDossierExamStatusUpdate)
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
