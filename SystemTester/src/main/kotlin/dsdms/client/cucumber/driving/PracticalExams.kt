package dsdms.client.cucumber.driving

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/driving/practicalExam.feature"],
    plugin = ["pretty", "summary"]
)
class PracticalExams : En {
    private val client: WebClient = VertxProviderImpl().getDrivingServiceClient()
    private var statusCode: Int? = null
    private var statusMessage: String? = null

    init {
        val sleeper = SmartSleep()

        When("registering a new exam appeal day on {word} with {int} places") {date: String, places: Int ->

        }
        Then("it receives {int} with {word}") {code: Int, message: String ->

        }


        When("request a list of appeals") {

        }
        Then("finds only one available appeal on {word}") {date: String ->

        }
    }
}