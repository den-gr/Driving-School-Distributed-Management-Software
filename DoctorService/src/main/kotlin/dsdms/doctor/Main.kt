package dsdms.doctor

import io.vertx.core.Vertx
import org.litote.kmongo.KMongo

class Main {
    companion object {
        private const val DEFAULT_MONGO_URI = "mongodb://admin:admin@localhost:27017"
        private const val DEFAULT_SERVER_PORT = 8030

        @JvmStatic
        fun main(args: Array<String>) {
            val port = if (System.getProperty("doctor_port") != null) {
                System.getProperty("doctor_port").toInt()
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
            println("Doctor service started")

            val dbConnection = KMongo
                .createClient(mongoURI)
                .getDatabase("doctor_service") //TODO check db name
            val server = Server(port, dbConnection)
            Vertx.vertx().deployVerticle(server)
        }
    }
}