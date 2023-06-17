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

package dsdms.driving

import io.vertx.core.Vertx
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

/**
 * Entry point of the program.
 */
object Main {
    private const val DEFAULT_MONGO_URI = "mongodb://admin:admin@localhost:27017"
    private const val DEFAULT_SERVER_PORT = 8010

    /**
     * Entry point of the program.
     * Use two main system parameters:
     *  - driving_port - the port that must use the server
     *  - mongo_uri - with uri of database
     */
    @JvmStatic
    fun main(args: Array<String>) {
        val port = if (System.getProperty("driving_port") != null) {
            System.getProperty("driving_port").toInt()
        } else {
            DEFAULT_SERVER_PORT
        }

        val mongoURI = if (System.getProperty("mongo_uri") != null) {
            System.getProperty("mongo_uri")
        } else {
            DEFAULT_MONGO_URI
        }

        println("Driving service started")

        val dbConnection = KMongo
            .createClient(mongoURI)
            .coroutine
            .getDatabase("driving_service")
        val server = Server(port, dbConnection)
        Vertx.vertx().deployVerticle(server)
    }
}
