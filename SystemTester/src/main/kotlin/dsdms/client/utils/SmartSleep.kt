package dsdms.client.utils

import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse

class SmartSleep(private val timeout: Long = 10000) {
    companion object {
        private const val DEBAG = false
        private const val INITIAL_DELAY = 50L
    }

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
