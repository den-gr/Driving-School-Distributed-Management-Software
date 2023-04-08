package dsdms.dossier

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler

class Server(val port: Int) : AbstractVerticle() {

    override fun start() {
        val router: Router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/api/:id").handler(::handle)
        //Static handler
//        router.route("/static/*").handler(StaticHandler.create("res/client/"))

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(port)

    }

    private fun handle(routingContext: RoutingContext){
        val id = routingContext.request().getParam("id").toIntOrNull()
        val response =  routingContext.response()
        if(id != null){
            response.setStatusCode(200).end(id.toString())
        }else{
            response.setStatusCode(401).end("wrong input")

        }
    }

}