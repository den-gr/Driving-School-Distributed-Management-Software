package dsdms.client.cucumber

import dsdms.utils.SmartSleep
import io.cucumber.java.*
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

lateinit var vertx: Vertx
lateinit var client: WebClient

@Before("@vertx")
fun before(){
    println("dsdms.client.cucumber.before")
    vertx = Vertx.vertx()
    client = WebClient.create(vertx)
}

@After("@vertx")
fun after(){
    println("dsdms.client.cucumber.after\n")
    vertx.close()
}

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],
    plugin = ["pretty", "summary"]
)
class TestCucumber: En {
    private var value: Int = -1
    private var response:  HttpResponse<Buffer>? = null

    init{
        val sleeper = SmartSleep()

        Given("I ask {int} id" ){ id: Int ->
            println("given $id")
            val request = client
                .get(8000, "localhost", "/api/$id")
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
                .get(8000, "localhost", "/api/$aa")
                .send()
            response = sleeper.waitResult(request)
        }
        Then("I have an error") {
            assertEquals(response?.statusCode(), 401)

        }
    }

}