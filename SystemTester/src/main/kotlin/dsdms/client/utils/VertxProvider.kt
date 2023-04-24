package dsdms.client.utils

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient

interface VertxProvider{
    fun getNewClient(): WebClient
}

class VertxProviderImpl : VertxProvider {
    private val vertx: Vertx = Vertx.vertx()

    override fun getNewClient(): WebClient{
        return WebClient.create(vertx)
    }
}