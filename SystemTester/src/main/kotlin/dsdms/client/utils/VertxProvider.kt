package dsdms.client.utils

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

interface VertxProvider {

    fun getDossierServiceClient(): WebClient

    fun getDrivingServiceClient(): WebClient
}

class VertxProviderImpl : VertxProvider {
    companion object {
        const val LOCALHOST: String = "localhost"
        const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
        const val DEFAULT_DRIVING_SERVICE_PORT = 8010
    }
    private val vertx: Vertx = Vertx.vertx()

    override fun getDossierServiceClient(): WebClient {
        val host = if (System.getProperty("dossier_host") != null) System.getProperty("dossier_host") else LOCALHOST
        val port = if (System.getProperty("dossier_port") != null) System.getProperty("dossier_port").toInt() else DEFAULT_DOSSIER_SERVICE_PORT

        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }

    override fun getDrivingServiceClient(): WebClient {
        val host = if (System.getProperty("driving_host") != null) System.getProperty("driving_host") else LOCALHOST
        val port = if (System.getProperty("driving_port") != null) System.getProperty("driving_port").toInt() else DEFAULT_DRIVING_SERVICE_PORT

        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}
