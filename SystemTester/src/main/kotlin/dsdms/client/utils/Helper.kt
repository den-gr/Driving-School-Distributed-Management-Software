package dsdms.client.utils

import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.assertNotNull

inline fun <reified T> createJson(docs: T): Buffer? {
    return Buffer.buffer(Json.encodeToString(docs))
}

fun checkResponse(res: HttpResponse<Buffer>?) {
    assertNotNull(res)
    assertNotNull(res.body())
}
