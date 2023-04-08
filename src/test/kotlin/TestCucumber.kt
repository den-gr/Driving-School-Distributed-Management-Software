import io.cucumber.java.Before
import io.cucumber.java8.En
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.Future
import io.vertx.core.Promise
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.fail

@RunWith(Cucumber::class)
@CucumberOptions(features = ["src/test/resources/test_feature.feature"])
class TestCucumber: En {
//    private fun client(vertx: Vertx): WebClient {
//        println("Hello Worldddd!")
//        val options = WebClientOptions().setKeepAlive(false)
//
//        return WebClient.create(vertx, options)
//    }

    init{
        val vertx = Vertx.vertx()
        var res: Future<HttpResponse<Buffer>>? = null
        Given("I ask {int} id" ){ id: Int ->
            println("given $id")
            res = WebClient.create(vertx)
                .get(8000, "localhost", "/api/$id")
                .send()



        }
        Then("I receive {int}"){ id: Int ->
            println("fine $id")
            res?.onSuccess {
                val value = it.body().toString().toIntOrNull()

                assertNotEquals(null, value)

                assertEquals(value,id)

                println("all good")
                fail("boom")
            }?.onComplete {
                println("vertx close")
                vertx.close()

            } ?: fail()
            println("ma perhcle")

        }
        Thread.sleep(2000)


        But("an error") {}
    }



}