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
    private var host = "localhost"
    init{
        if(System.getProperty("dossier_host") != null){
            host = System.getProperty("dossier_host")
        }
    }

    override fun getNewClient(): WebClient{
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(8000)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}