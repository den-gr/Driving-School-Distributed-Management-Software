package dsdms.doctor

import com.mongodb.client.MongoDatabase
import dsdms.doctor.database.Repository
import dsdms.doctor.database.RepositoryImpl
import dsdms.doctor.handlers.RouteHandlers
import dsdms.doctor.handlers.RouteHandlersImpl
import dsdms.doctor.model.ModelImpl
import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class Server(private val port: Int, dbConnection: MongoDatabase) : CoroutineVerticle() {

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
        router.post("/doctorSlots").coroutineHandler(handlersImpl::bookDoctorVisit)
        router.get("/doctorSlots").handler(handlersImpl::getBookedDoctorSlots)
        router.delete("/doctorSlots/:dossierId").handler(handlersImpl::deleteDoctorSlot)
    }
}