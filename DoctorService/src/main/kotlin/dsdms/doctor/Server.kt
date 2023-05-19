package dsdms.doctor

import dsdms.doctor.database.Repository
import dsdms.doctor.database.RepositoryImpl
import dsdms.doctor.handlers.RouteHandlers
import dsdms.doctor.handlers.RouteHandlersImpl
import dsdms.doctor.model.ModelImpl
import dsdms.doctor.model.domainServices.vertxClient.VertxClientProvider
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

    private suspend fun initializeModel(): RouteHandlersImpl {
        return RouteHandlersImpl(
            ModelImpl(repository, VertxClientProvider(vertx).getDossierServiceClient())
        )
    }

    override suspend fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        setRoutes(router, initializeModel())

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

    private fun setRoutes(router: Router, handlersImpl: RouteHandlers) {
        router.post("/doctorSlots").coroutineHandler(handlersImpl::bookDoctorVisit)
        router.get("/doctorSlots").coroutineHandler(handlersImpl::getBookedDoctorSlots)
        router.delete("/doctorSlots/:dossierId").coroutineHandler(handlersImpl::deleteDoctorSlot)
    }
}