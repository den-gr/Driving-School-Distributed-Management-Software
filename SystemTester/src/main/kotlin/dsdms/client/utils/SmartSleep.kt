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

import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse

/**
 * Smart timer that wait for server responses.
 * @param timeout .> time to wait before throwing exception.
 */
class SmartSleep(private val timeout: Long = 10000) {
    companion object {
        private const val DEBAG = false
        private const val INITIAL_DELAY = 50L
    }

    /**
     * @param future result.
     * @return server response after smart sleep.
     */
    @Suppress("ReturnCount")
    fun waitResult(future: Future<HttpResponse<Buffer>>): HttpResponse<Buffer>? {
        future.onFailure { println(it.message) }

        var toWait = timeout
        val iterable = iterator {
            var x = INITIAL_DELAY
            while (true) {
                yield(x)
                x *= 2
            }
        }
        for (i in iterable) {
            if (DEBAG) print(".")
            if (toWait < i) {
                Thread.sleep(toWait)
                return future.result()
            }
            Thread.sleep(i)
            if (future.isComplete) {
                if (DEBAG) println(" is complete on $i")
                return future.result()
            }
            toWait -= i
        }
        return null
    }
}
