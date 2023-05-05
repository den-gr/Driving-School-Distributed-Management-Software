package dsdms.driving

import io.vertx.core.Vertx
import org.litote.kmongo.KMongo

class Main {
    companion object{
        @JvmStatic
        fun main(args : Array<String>){
            val port = 8010
            println("Driving service started")

            val dbConnection = KMongo
                .createClient("mongodb://admin:admin@mongo:27017")
                .getDatabase("driving_service")
            val server = Server(port, dbConnection)
            Vertx.vertx().deployVerticle(server)
        }
    }
}

