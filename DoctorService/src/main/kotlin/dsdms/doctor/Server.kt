package dsdms.doctor

import com.mongodb.client.MongoDatabase
import dsdms.doctor.database.Repository
import dsdms.doctor.database.RepositoryImpl
import dsdms.doctor.handlers.RouteHandlers
import dsdms.doctor.handlers.RouteHandlersImpl
import dsdms.doctor.model.ModelImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
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
        router.post("/doctorVisits").handler(handlersImpl::bookDoctorVisit)
        router.get("/doctorVisits").handler(handlersImpl::getBookedDoctorSlots)
        router.delete("/doctorVisits/:dossierId").handler(handlersImpl::deleteDoctorSlot)
        router.get("/test").handler(this::testHandler)
    }

    private fun testHandler(routingContext: RoutingContext) {
        routingContext.response().setStatusCode(200).end("777")
    }
}