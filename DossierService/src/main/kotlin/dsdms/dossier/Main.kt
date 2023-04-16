package dsdms.dossier

import io.vertx.core.Vertx

class Main {
    companion object{
        @JvmStatic
        fun main(args : Array<String>){
            val port = 8000
            println("wow")

            val server = Server(port)
            Vertx.vertx().deployVerticle(server)
        }
    }
}

