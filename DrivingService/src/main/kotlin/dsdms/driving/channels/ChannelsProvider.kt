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
