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

package dsdms.client.utils

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions

/**
 * Gives connections to each service.
 */
interface VertxProvider {

    /**
     * @return webclient connected to dossier service.
     */
    fun getDossierServiceClient(): WebClient

    /**
     * @return webclient connected to driving service.
     */
    fun getDrivingServiceClient(): WebClient

    /**
     * @return webclient connected to exam service.
     */
    fun getExamServiceClient(): WebClient

    /**
     * @return webclient connected to doctor service.
     */
    fun getDoctorServiceClient(): WebClient
}

/**
 * Implements Vertx Provider.
 */
class VertxProviderImpl : VertxProvider {
    companion object {
        private const val LOCALHOST: String = "localhost"
        private const val DEFAULT_DOSSIER_SERVICE_PORT = 8000
        private const val DEFAULT_DRIVING_SERVICE_PORT = 8010
        private const val DEFAULT_EXAM_SERVICE_PORT = 8020
        private const val DEFAULT_DOCTOR_SERVICE_PORT = 8030
    }
    private val vertx: Vertx = Vertx.vertx()

    override fun getDossierServiceClient(): WebClient {
        return createClient(getHost("dossier_host"), getPort("dossier_port", DEFAULT_DOSSIER_SERVICE_PORT))
    }

    override fun getDrivingServiceClient(): WebClient {
        return createClient(getHost("driving_host"), getPort("driving_port", DEFAULT_DRIVING_SERVICE_PORT))
    }

    override fun getExamServiceClient(): WebClient {
        return createClient(getHost("exam_host"), getPort("exam_port", DEFAULT_EXAM_SERVICE_PORT))
    }

    override fun getDoctorServiceClient(): WebClient {
        return createClient(getHost("doctor_host"), getPort("doctor_port", DEFAULT_DOCTOR_SERVICE_PORT))
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
