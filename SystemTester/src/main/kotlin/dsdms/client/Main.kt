package dsdms.client

import io.cucumber.core.cli.Main

/**
 * Entry point of testing system.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Main.main(
            "-p",
            "pretty",
            "--glue",
            "dsdms.client.cucumber",
            "--plugin",
            "html:build/reports/cucumber",
            "classpath:features",
        )
    }
}
