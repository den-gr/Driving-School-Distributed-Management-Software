package dsdms.client.utils

import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientOptions
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

interface VertxProvider{
    fun getNewClient(): WebClient
}

class VertxProviderImpl : VertxProvider {
    private val vertx: Vertx = Vertx.vertx()

    override fun getNewClient(): WebClient{
        var host = "localhost"
        if(System.getProperty("docker") != null){
            host = "dossier_service"
        }
        println("Host is: $host || while docker property = ${System.getProperty("docker")}")
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(8000)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}