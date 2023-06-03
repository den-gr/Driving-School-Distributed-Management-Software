package dsdms.driving

import dsdms.driving.channels.ChannelsProvider
import dsdms.driving.channels.ChannelsProviderImpl
import dsdms.driving.database.Repository
import dsdms.driving.database.RepositoryImpl
import dsdms.driving.handlers.RouteHandlers
import dsdms.driving.handlers.RouteHandlersImpl
import dsdms.driving.model.ModelImpl
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
        val channelProvider: ChannelsProvider = ChannelsProviderImpl(vertx)
        val handlers: RouteHandlers = RouteHandlersImpl(ModelImpl(repository, channelProvider))

        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        setRoutes(router, handlers)

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onFailure {
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
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

    private fun setRoutes(router: Router, handlers: RouteHandlers) {
        router.post("/drivingSlots").coroutineHandler(handlers::registerNewDrivingSlot)
        router.get("/drivingSlots").coroutineHandler(handlers::getOccupiedDrivingSlots)

        router.delete("/drivingSlots/:id").coroutineHandler(handlers::deleteDrivingSlot)

        router.post("/practicalExamDays").coroutineHandler(handlers::postPracticalExamDay)
        router.get("/practicalExamDays").coroutineHandler(handlers::getPracticalExamDays)
    }
}
