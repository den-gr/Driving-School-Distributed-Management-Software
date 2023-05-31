package dsdms.exam.channels


import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

interface ChannelsProvider {
    val dossierServiceChannel: DossierServiceChannel
}

class ChannelsProviderImpl(private val vertx: Vertx): ChannelsProvider {
    override val dossierServiceChannel: DossierServiceChannel

    companion object {
        const val LOCALHOST: String = "localhost"
        const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
    }

    init {
        dossierServiceChannel = getDossierServiceClient()
    }

    private fun getDossierServiceClient(): DossierServiceChannel {
        val client = createClient(getHost(), getPort())
        return DossierServiceChannelImpl(client)
    }

    private fun getHost(hostName: String = "dossier_host"): String{
        return if (System.getProperty(hostName) != null) System.getProperty(hostName) else LOCALHOST
    }

    private fun getPort(portName: String = "dossier_port", defaultPort: Int = DEFAULT_DOSSIER_SERVICE_PORT): Int {
        return  if (System.getProperty(portName) != null) System.getProperty(portName).toInt() else defaultPort
    }

    private fun createClient(host: String, port: Int): WebClient {
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}