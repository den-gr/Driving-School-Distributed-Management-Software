package dsdms.driving.channels

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

/**
 * Provide a channel that allows to communicate with other bounded contexts.
 */
interface ChannelsProvider {
    /**
     * Allows to communicate with exam context.
     */
    val examServiceChannel: ExamServiceChannel
}

/**
 * Channels provider implementation based on vertx.
 * @param vertx
 */
class ChannelsProviderImpl(private val vertx: Vertx) : ChannelsProvider {
    override val examServiceChannel: ExamServiceChannel

    companion object {
        private const val LOCALHOST: String = "localhost"
        private const val DEFAULT_EXAM_SERVICE_PORT = 8020
    }

    init {
        examServiceChannel = getExamServiceClient()
    }

    private fun getExamServiceClient(): ExamServiceChannel {
        val client = createClient(getHost(), getPort())
        return ExamServiceChannelImpl(client)
    }

    private fun getHost(hostName: String = "exam_host"): String {
        return if (System.getProperty(hostName) != null) System.getProperty(hostName) else LOCALHOST
    }

    private fun getPort(portName: String = "exam_port", defaultPort: Int = DEFAULT_EXAM_SERVICE_PORT): Int {
        return if (System.getProperty(portName) != null) System.getProperty(portName).toInt() else defaultPort
    }

    private fun createClient(host: String, port: Int): WebClient {
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}
