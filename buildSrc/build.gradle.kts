/**
 * »buildSrc« is a directory at the project root level which contains build info.
 * We can use this directory to enable kotlin-dsl
 * and write logic related to custom configuration
 * and share them across the project.
 * It was one of the most used approaches in recent days because of its testability.
 *
 * Gradle Doc:
 *
 * The directory »buildSrc« is treated as an included build.
 * Upon discovery of the directory, Gradle automatically compiles and tests
 * this code and puts it in the classpath of your build script.
 * There can be only one buildSrc directory for multi-project builds,
 * which has to sit in the root project directory.
 * »buildSrc« should be preferred over script plugins as it is easier
 * to maintain, refactor, and test the code.
 */
plugins { `kotlin-dsl` }

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()// so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
}
