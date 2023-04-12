package dsdms.client

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = [
        "src/test/resources/features/test_feature.feature",
        //"src/test/resources/features/DossierService/*.feature"
   ],
    plugin = ["pretty", "summary"]
)
class MainCucumberRunner