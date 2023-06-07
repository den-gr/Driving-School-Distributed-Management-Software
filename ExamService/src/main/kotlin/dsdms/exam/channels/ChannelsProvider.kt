package dsdms.exam.channels

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

/**
 * Provide a channel that allows to communicate with other bounded contexts.
 */
interface ChannelsProvider {

    /**
     * Given connection to dossier service.
     */
    val dossierServiceChannel: DossierServiceChannel
}

/**
 * Provides connection to other services.
 * @param vertx -> Given vertx thread to be used.
 */
class ChannelsProviderImpl(private val vertx: Vertx) : ChannelsProvider {
    override val dossierServiceChannel: DossierServiceChannel

    companion object {
        private const val LOCALHOST: String = "localhost"
        private const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
    }

    init {
        dossierServiceChannel = getDossierServiceClient()
    }

    private fun getDossierServiceClient(): DossierServiceChannel {
        val client = createClient(getHost(), getPort())
        return DossierServiceChannelImpl(client)
    }

    private fun getHost(hostName: String = "dossier_host"): String {
        return if (System.getProperty(hostName) != null) System.getProperty(hostName) else LOCALHOST
    }

    private fun getPort(portName: String = "dossier_port", defaultPort: Int = DEFAULT_DOSSIER_SERVICE_PORT): Int {
        return if (System.getProperty(portName) != null) System.getProperty(portName).toInt() else defaultPort
    }

    private fun createClient(host: String, port: Int): WebClient {
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}
