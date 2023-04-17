package client.cucumber

import dsdms.client.SmartSleep
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonObject
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Serializable
data class SubscriberDocuments(val name: String,
                               val surname: String, val fiscal_code: String)

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/registerAndReadSubscriber.feature"],
    plugin = ["pretty", "summary"]
)
class Dossier : En {
    private var value: Int = -1

    init {
        val sleeper = SmartSleep()
        When("I send {word}, {word}, {word} to server") {name: String, surname: String, fiscal_code: String ->
            val request = client
                .post(8000, "localhost", "/dossiers")
                .sendBuffer(Buffer.buffer(Json.encodeToString(SubscriberDocuments(name, surname, fiscal_code))))
            val response = sleeper.waitResult(request)

            assertNotNull(response)
            value = response.body().toString().toInt()
        }
        Then("I received Id of registered dossier") {
            println("Value: $value")
            assertEquals(0, value)
        }
    }
}