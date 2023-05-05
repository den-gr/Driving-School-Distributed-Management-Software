package dsdms.client.cucumber

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import io.cucumber.java.*
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/test_feature.feature"],
    plugin = ["pretty", "summary"]
)
class TestCucumber: En {
    private val client: WebClient = VertxProviderImpl().getNewClient(8000)
    private var value: Int = -1
    private var response:  HttpResponse<Buffer>? = null

    init{
        val sleeper = SmartSleep()

        Given("I ask {int} id" ){ id: Int ->
            println("given $id")
            val request = client
                .get("/api/$id")
                .send()
            val response = sleeper.waitResult(request)
            assertNotNull(response)
            value = response.body().toString().toInt()
        }
        Then("I receive {int} that is equal to input"){ id: Int ->
            assertEquals(value,id)
        }
        But("an error") {}

        Given("an incorrect input as {word}") {aa: String ->
            val request = client
                .get("/api/$aa")
                .send()
            response = sleeper.waitResult(request)
            assertNotNull(response)
        }
        Then("I have an error") {
            assertEquals(response?.statusCode(), 401)

        }
    }

}