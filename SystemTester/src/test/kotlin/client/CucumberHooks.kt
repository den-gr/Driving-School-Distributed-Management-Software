package client

import io.cucumber.java.After
import io.cucumber.java.Before
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient

private val vertx: Vertx = Vertx.vertx()
private lateinit var client: WebClient

@Before("@vertx")
fun before(){
    client = WebClient.create(vertx)
}

@After("@vertx")
fun after(){
    client.close()
    println("client.close()\n")
}

fun getWebClient(): WebClient {
    return client
}