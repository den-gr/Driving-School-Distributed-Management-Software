package dsdms.dossier

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import io.vertx.core.Vertx

import org.litote.kmongo.*

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


data class Dossier(val name: String, val number: Int)

fun main(args: Array<String>) {
    val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
    val database = client.getDatabase("dsdms")
    val col = database.getCollection<Dossier>("Dossier")
    database.listCollectionNames().forEach { println(it) }
//    println(col.)
    col.find().forEach{ println(it) }
    println("Hello Worldddd!")
}