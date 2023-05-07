package dsdms.driving

import io.vertx.core.Vertx
import org.litote.kmongo.KMongo

class Main {
    companion object {
        private const val DEFAULT_MONGO_URI = "mongodb://admin:admin@localhost:27017"
        private const val DEFAULT_SERVER_PORT = 8010

        @JvmStatic
        fun main(args: Array<String>) {
            val port = if (System.getProperty("driving_port") != null) {
                System.getProperty("driving_port").toInt()
            } else {
                DEFAULT_SERVER_PORT
            }

            val mongoURI = if (System.getProperty("mongo_uri") != null) {
                System.getProperty("mongo_uri")
            } else {
                DEFAULT_MONGO_URI
            }

            println("Port: $port")
            println("MongoURI: $mongoURI")
            println("Driving service started")

            val dbConnection = KMongo
                .createClient(mongoURI)
                .getDatabase("driving_service")
            val server = Server(port, dbConnection)
            Vertx.vertx().deployVerticle(server)
        }
    }
}
