package dsdms.driving

import com.mongodb.client.MongoDatabase
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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import org.litote.kmongo.coroutine.CoroutineDatabase
import kotlin.system.exitProcess

@DelicateCoroutinesApi
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
            .onFailure{
                println("Vertx failure: ${it.message}")
                exitProcess(1)
            }
    }

    private fun setRoutes(router: Router) {
        router.post("/drivingSlots").handler(handlersImpl::registerDrivingSlot)
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