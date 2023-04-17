package client.cucumber

import dsdms.client.SmartSleep
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.json.JsonObject
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun giveJsonObject(name: String, surname: String, fiscal_code: String): JsonObject {
    return JsonObject.of(
        "name", name,
        "surname", surname,
        "fiscal_code", fiscal_code
    )
}

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
                .sendJson(giveJsonObject(name, surname, fiscal_code))
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