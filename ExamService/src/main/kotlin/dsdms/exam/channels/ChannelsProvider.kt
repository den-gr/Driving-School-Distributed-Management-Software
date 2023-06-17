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
