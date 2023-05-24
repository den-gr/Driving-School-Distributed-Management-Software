package dsdms.doctor.model.domainServices.vertxClient

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

class VertxClientProvider(private val vertx: Vertx): VertxClient {
    companion object {
        const val LOCALHOST: String = "localhost"
        const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
        const val DEFAULT_EXAM_SERVICE_PORT = 8020
    }

    override suspend fun getDossierServiceClient(): WebClient {
        return createClient(getHost("dossier_host"), getPort("dossier_port", DEFAULT_DOSSIER_SERVICE_PORT))
    }

    override suspend fun getExamServiceClient(): WebClient {
        return createClient(getHost("exam_host"), getPort("exam_port", DEFAULT_EXAM_SERVICE_PORT))
    }

    private fun getHost(hostName: String): String{
        return if (System.getProperty(hostName) != null) System.getProperty(hostName) else LOCALHOST
    }

    private fun getPort(portName: String, defaultPort: Int): Int {
        return  if (System.getProperty(portName) != null) System.getProperty(portName).toInt() else defaultPort
    }

    private fun createClient(host: String, port: Int): WebClient {
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}