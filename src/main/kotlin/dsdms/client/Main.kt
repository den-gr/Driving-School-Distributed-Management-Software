package dsdms.client

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

class Main{
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            println("Hello Worldddd!")
            val options = WebClientOptions().setKeepAlive(false)
            val vertx = Vertx.vertx()
            val client: WebClient = WebClient.create(vertx, options)
            client.get(8000, "localhost", "/api/999")
                .send()
                .onSuccess { println("Response: " + it.body()) }
                .onFailure { println("Karaul") }
            vertx.close()
        }
    }

}
