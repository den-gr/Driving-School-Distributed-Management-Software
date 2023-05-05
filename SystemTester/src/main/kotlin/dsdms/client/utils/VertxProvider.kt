package dsdms.client.utils

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

interface VertxProvider{
    fun getNewClient(port: Int): WebClient
}

class VertxProviderImpl : VertxProvider {
    private val vertx: Vertx = Vertx.vertx()
    private var host = "localhost"
    init{
        if(System.getProperty("dossier_host") != null){
            host = System.getProperty("dossier_host")
        }
    }

    override fun getNewClient(port: Int): WebClient{
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}