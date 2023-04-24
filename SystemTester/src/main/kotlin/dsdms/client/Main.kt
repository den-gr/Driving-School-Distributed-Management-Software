package dsdms.client

import io.cucumber.core.cli.Main

class Main{
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            Main.main(
                "-p", "pretty",
                "--glue", "dsdms.client.cucumber",
                "--plugin", "html:build/reports/cucumber",
                "classpath:features")
        }
    }
}