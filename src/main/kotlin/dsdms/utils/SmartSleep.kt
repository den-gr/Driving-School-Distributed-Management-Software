package dsdms.utils

import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse

class SmartSleep(private val timeout: Long = 5000) {

    fun waitResult(feature: Future<HttpResponse<Buffer>>): HttpResponse<Buffer>? {
        var toWait = timeout
        val iterable = iterator {
            var x = 50L
            while(true){
                yield(x)
                x *= 2
            }
        }
        for(i in iterable){
            println("i = $i | toWait = $toWait")
            if(toWait < i){
                Thread.sleep(toWait)
                return feature.result()
            }
            Thread.sleep(i)
            if(feature.isComplete){
                println("is complete on $i")
                return feature.result()
            }
            toWait -= i
        }
        return null

    }
}

