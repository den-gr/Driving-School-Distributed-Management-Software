rootProject.name = "DSDMS"

include(":DossierService")
include(":SystemTester")
include(":DrivingService")

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.0.23"
}

gitHooks {
    commitMsg {
        conventionalCommits()
    }
    createHooks(true)
}