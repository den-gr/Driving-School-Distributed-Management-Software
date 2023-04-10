package dsdms.client.test

import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/test"],
    plugin = ["pretty", "summary"]
)
class AnotherFeatureSteps : En {
    private var number = -1
    init {
        Given("a big {int}") { num: Int ->
            number = num
        }
        Then("Print this number") {
            println(number)
        }
    }
}