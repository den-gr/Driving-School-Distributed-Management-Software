package dsdms.dossier

import dsdms.dossier.database.Repository
import dsdms.dossier.database.RepositoryImpl
import dsdms.dossier.handlers.RouteHandlers
import dsdms.dossier.handlers.RouteHandlersImpl
import dsdms.dossier.model.ModelImpl
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import org.litote.kmongo.coroutine.CoroutineDatabase
import kotlin.system.exitProcess

class Server(private val port: Int, dbConnection: CoroutineDatabase) : AbstractVerticle() {

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
        router.get("/api/:id").handler(::handle)
        router.post("/dossiers").handler(handlersImpl::handleDossierRegistration)
        router.get("/dossiers/:id").handler(handlersImpl::handleDossierIdReading)
        router.put("/dossiers/:id").handler(handlersImpl::handleDossierExamStatusUpdate)
        router.delete("/dossiers/:id").handler(handlersImpl::deleteDossier)
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