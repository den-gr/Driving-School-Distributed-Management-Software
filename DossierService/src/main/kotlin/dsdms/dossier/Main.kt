package dsdms.dossier

import io.vertx.core.Vertx
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

class Main {
    companion object{
        @JvmStatic
        fun main(args : Array<String>){
            val port = 8000
            println("Started")

            val dbConnection = KMongo
                .createClient("mongodb://admin:admin@mongo:27017")
                .coroutine
                .getDatabase("dossier_service")
            val server = Server(port, dbConnection)
            Vertx.vertx().deployVerticle(server)
        }
    }
}

