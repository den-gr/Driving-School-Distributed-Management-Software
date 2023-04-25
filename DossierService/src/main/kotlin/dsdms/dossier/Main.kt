package dsdms.dossier

import io.vertx.core.Vertx

import org.litote.kmongo.*

class Main {
    companion object{
        @JvmStatic
        fun main(args : Array<String>){
            val port = 8000
            println("wow")

            val dossierServiceDb = KMongo.createClient("mongodb://admin:admin@mongo:27017").getDatabase("dossier_service")
            val server = Server(port, dossierServiceDb)
            Vertx.vertx().deployVerticle(server)
        }
    }
}