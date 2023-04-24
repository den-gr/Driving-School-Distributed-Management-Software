package dsdms.dossier

import com.mongodb.client.MongoDatabase
import dsdms.dossier.handlers.RouteHandlers
import dsdms.dossier.handlers.RouteHandlersImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import kotlin.system.exitProcess

class Server(private val port: Int, dossierServiceDb: MongoDatabase) : AbstractVerticle() {

    private val handlersImpl: RouteHandlers = RouteHandlersImpl(dossierServiceDb)
    override fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.get("/api/:id").handler(::handle)
        router.post("/dossiers").handler(handlersImpl::handleDossierRegistration)
        router.get("/dossiers/:id").handler(handlersImpl::handleDossierIdReading)
        router.put("/dossiers/:id").handler(handlersImpl::handleDossierExamStatusUpdate)

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)
            .onFailure{
                println("Vertx failure: ${it.message}")
                exitProcess(1)
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