@file:Suppress("UnstableApiUsage")

include(":app")
// ^IDE may add »include()« line on top when refactoring…
// since Gradle 5.0 DO NOT »include(":buildSrc")« here as it is a reserved name.
includeBuild("buildPlg")
rootProject.name = "composite-build-demo"

// https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
