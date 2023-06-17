/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

package dsdms.doctor.channels

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

/**
 * Provide channels for communication with other bounded contexts.
 */
interface ChannelsProvider {
    /**
     * Channel to DossierContext.
     */
    val dossierServiceChannel: DossierServiceChannel

    /**
     * Channel to ExamContext.
     */
    val examServiceChannel: ExamServiceChannel
}

/**
 * Vertx channels provider.
 */
class ChannelsProviderImpl(private val vertx: Vertx) : ChannelsProvider {
    override val dossierServiceChannel: DossierServiceChannel
    override val examServiceChannel: ExamServiceChannel

    companion object {
        private const val LOCALHOST: String = "localhost"
        private const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
        private const val DEFAULT_EXAM_SERVICE_PORT = 8020
    }

    init {
        dossierServiceChannel = getDossierServiceClient()
        examServiceChannel = getExamServiceClient()
    }

    private fun getDossierServiceClient(): DossierServiceChannel {
        val client = createClient(getHost("dossier_host"), getPort("dossier_port", DEFAULT_DOSSIER_SERVICE_PORT))
        return DossierServiceChannelImpl(client)
    }

    private fun getExamServiceClient(): ExamServiceChannel {
        val client = createClient(getHost("exam_host"), getPort("exam_port", DEFAULT_EXAM_SERVICE_PORT))
        return ExamServiceChannelImpl(client)
    }

    private fun getHost(hostName: String): String {
        return if (System.getProperty(hostName) != null) System.getProperty(hostName) else LOCALHOST
    }

    private fun getPort(portName: String, defaultPort: Int): Int {
        return if (System.getProperty(portName) != null) System.getProperty(portName).toInt() else defaultPort
    }

    private fun createClient(host: String, port: Int): WebClient {
        val options: WebClientOptions = WebClientOptions()
            .setDefaultPort(port)
            .setDefaultHost(host)
        return WebClient.create(vertx, options)
    }
}
