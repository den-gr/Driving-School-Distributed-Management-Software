package dsdms.driving

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

    private fun setRoutes(router: Router) {
        router.post("/drivingSlots").coroutineHandler(handlersImpl::registerNewDrivingSlot)
        router.get("/drivingSlots").coroutineHandler(handlersImpl::getOccupiedDrivingSlots)
        router.delete("/drivingSlots/:id").coroutineHandler(handlersImpl::deleteDrivingSlot)
        router.post("/practicalExamDays").coroutineHandler(handlersImpl::postPracticalExamDay)
        router.get("/practicalExamDays").coroutineHandler(handlersImpl::getPracticalExamDays)
    }
}
