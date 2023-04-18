package dsdms.dossier

import dsdms.dossier.handlers.RouteHandlers
import dsdms.dossier.handlers.RouteHandlersImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import kotlin.system.exitProcess

class Server(private val port: Int) : AbstractVerticle() {

    private val handlersImpl: RouteHandlers = RouteHandlersImpl()
    override fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.get("/api/:id").handler(::handle)
        router.post("/dossiers").handler(handlersImpl::handleDossierRegistration)
        router.get("/dossiers/:id").handler(handlersImpl::handleDossierIdReading)
        router.get("/mongo").handler(handlersImpl::testHandler)

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