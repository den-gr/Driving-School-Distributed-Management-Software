package dsdms.client.utils

import io.vertx.core.buffer.Buffer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


inline fun<reified T> createJson(docs: T): Buffer? {
    return Buffer.buffer(Json.encodeToString(docs))
}