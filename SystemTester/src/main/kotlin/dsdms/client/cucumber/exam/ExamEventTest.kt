package dsdms.client.cucumber.exam

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/exam/examTest.feature"],
    plugin = ["pretty", "summary"]
)
class ExamEventTest: En {
    private val client: WebClient = VertxProviderImpl().getExamServiceClient()
    private var value: Int = -1

    init{
        val sleeper = SmartSleep()
        When("I send a request to server"){
            val request = client
                .get("/test")
                .send()
            val response = sleeper.waitResult(request)
            assertNotNull(response)
            value = response.body().toString().toInt()

        }

        Then("I received {int}"){value: Int ->
            assertEquals(999, value)
        }
    }
}