package dsdms.dossier

import io.vertx.core.Vertx
import kotlinx.coroutines.DelicateCoroutinesApi
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

class Main {
    companion object{
        private const val DEFAULT_MONGO_URI = "mongodb://admin:admin@localhost:27017"
        private const val DEFAULT_SERVER_PORT = 8000


        @JvmStatic
        @DelicateCoroutinesApi
        fun main(args : Array<String>){


            val port = if (System.getProperty("dossier_port") != null) System.getProperty("dossier_port").toInt()
                else DEFAULT_SERVER_PORT

            val mongoURI = if (System.getProperty("mongo_uri") != null) System.getProperty("mongo_uri")
                else DEFAULT_MONGO_URI

            println("Port: $port")
            println("MongoURI: $mongoURI")
            println("Dossier service started")

            val dbConnection = KMongo
                .createClient(mongoURI)
                .coroutine
                .getDatabase("dossier_service")
            val server = Server(port, dbConnection)
            Vertx.vertx().deployVerticle(server)
        }
    }
}

