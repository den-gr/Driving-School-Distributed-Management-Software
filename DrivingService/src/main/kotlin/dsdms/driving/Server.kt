package dsdms.driving

import com.mongodb.client.MongoDatabase
import dsdms.driving.database.Repository
import dsdms.driving.database.RepositoryImpl
import dsdms.driving.handlers.RouteHandlers
import dsdms.driving.handlers.RouteHandlersImpl
import dsdms.driving.model.ModelImpl
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
            .onFailure{
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
    }

    private fun setRoutes(router: Router) {
        router.get("/drivingSlots").handler(handlersImpl::getOccupiedDrivingSlots)
    }

//    private fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
//        handler { ctx ->
//            launch(Vertx.currentContext().dispatcher()) {
//                try {
//                    fn(ctx)
//                } catch (e: Exception) {
//                    ctx.fail(e)
//                }
//            }
//        }
//    }

    private fun handle(routingContext: RoutingContext){
        val id = routingContext.request().getParam("id").toIntOrNull()
        val response =  routingContext.response()
        if(id != null){
            response.setStatusCode(200).end((id).toString())
        }else{
            response.setStatusCode(401).end("wrong input")
        }
    }

}