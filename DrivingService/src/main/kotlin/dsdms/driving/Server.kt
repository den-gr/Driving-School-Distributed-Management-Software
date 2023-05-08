package dsdms.driving

import com.mongodb.client.MongoDatabase
import dsdms.driving.database.Repository
import dsdms.driving.database.RepositoryImpl
import dsdms.driving.handlers.RouteHandlers
import dsdms.driving.handlers.RouteHandlersImpl
import dsdms.driving.model.ModelImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import kotlin.system.exitProcess

class Server(private val port: Int, dbConnection: MongoDatabase) : AbstractVerticle() {

    private val repository: Repository = RepositoryImpl(dbConnection)
    private val handlersImpl: RouteHandlers = RouteHandlersImpl(ModelImpl(repository))

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
        router.post("/drivingSlots").handler(handlersImpl::registerNewDrivingSlot)
        router.get("/drivingSlots").handler(handlersImpl::getOccupiedDrivingSlots)
    }
}
