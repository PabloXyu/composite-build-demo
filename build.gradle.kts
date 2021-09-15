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

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
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
