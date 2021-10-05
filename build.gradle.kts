// Top-level build file where you can add configuration options
// common to all sub-projects/modules.

/**
 * Kotlin DSL Pros:
 * ==================================
 *  • Simplified plugins syntax
 *  • Default lazy task configuration
 *  • Compile time checks
 *  • Better IDE experience
 *  • It’s Kotlin!
 */

// plugin{} block always first!
plugins { java }

/**
 * The buildscript block is where you configure
 * the repositories and dependencies for Gradle itself—meaning,
 * you should not include dependencies for your modules here.
 *
 * For example,
 * this block includes the Android plugin for Gradle as a dependency
 * because it provides the additional instructions
 * Gradle needs to build Android app modules.
 */
buildscript {
    /**
     * The repositories block configures the repositories
     * Gradle uses to search or download the dependencies.
     * Gradle pre-configures support for remote repositories
     * such as Google, Maven Central, and Gradle Plugin Portal.
     * You can also use local repositories or define your own remote repositories.
     */
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        // NOTE: Do not place your application dependencies here,
        // they belong in the individual module build.gradle files
    }
}

// the snippet below makes sure that all
// src/[main|test|androidTest]/kotlin dirs
// are Source Roots for each module/subproject

sourceSets.all {
    java.srcDir("src/$name/kotlin")
}
