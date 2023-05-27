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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.CoroutineDatabase
import kotlin.system.exitProcess

@DelicateCoroutinesApi
class Server(private val port: Int, dbConnection: CoroutineDatabase) : CoroutineVerticle() {

    private val repository: Repository = RepositoryImpl(dbConnection)

    private val handlersImpl: RouteHandlers = RouteHandlersImpl(ModelImpl(repository))
    override suspend fun start() {
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

    /**
     * TODO: Insert a route to disable a dossier and delete
     *      all provisional licenses and theoretical exam passes with that id
     */
    private fun setRoutes(router: Router) {
        router.get("/api/:id").handler(::handle)
        router.post("/dossiers").coroutineHandler(handlersImpl::handleDossierRegistration)
        router.get("/dossiers/:id").coroutineHandler(handlersImpl::handleDossierIdReading)
        router.put("/dossiers/:id/examStatus").coroutineHandler(handlersImpl::handleDossierExamStatusUpdate)
        router.put("/dossiers/:id/examAttempts").coroutineHandler(handlersImpl::handleDossierExamAttemptsUpdate)
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

    private fun handle(routingContext: RoutingContext) {
        val id = routingContext.request().getParam("id").toIntOrNull()
        val response = routingContext.response()
        if (id != null) {
            response.setStatusCode(200).end((id).toString())
        } else {
            response.setStatusCode(401).end("wrong input")
        }
    }
}
