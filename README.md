<!-- "gradle-as" is a png picture of logos AS IDE, Android and Gradle together -->
![gradle-as](https://user-images.githubusercontent.com/26512179/167963969-778cfece-769d-4da9-b23a-e4a08e3781bd.png)
<br/>

## NOTICE:

## This repository is actually a clone of [ app-build-demo](https://github.com/PabloXyu/app-build-demo) code, but right properly named commits correspond to steps of included tutorial/procedure of [App Build Demo](https://github.com/PabloXyu/app-build-demo).
<!-- 3px blue line -->
![](https://user-images.githubusercontent.com/26512179/167968709-139094d0-f85f-4fd3-ba1a-b4daeb22ce50.png)
# Gradle<sup>🐘</sup> Composite Build Demo Application
The purpose of the project is to extend my Android programming knowledge,
showing my coding skills for job interview, and share my knowledge<
with other programmers for the benefit of this worldwide android-enthusiastic community.
<br></br>
This is my first published repository.  
This app code is written on [*Android Studio Arctic Fox IDE (2020.3.1, Patch 2)*](https://developer.android.com/studio#downloads).
<br></br>
> ### <sup>☑ </sup> What Is Gradle?
> Gradle is an open-source build automation system  
> that helps you manipulate the build process and its logic.
> For example, when you build an app,  
> it’s Gradle that compiles all the code and creates an APK for you.
>
> ### <sup>☑ </sup> Usage of terms *module* & *project*
> Although Google Android documentation uses term *project* and its *modules*,  
> Gradle documentation names *module* as *project* or *subproject*.

<br/>
> ### <sup>☑ </sup> Root Script `build.gradle`
>
> #### `buildscript{}` block
> The `buildscript{}` block is where you configure  
> the repositories and dependencies for Gradle itself—meaning,  
> you should not include dependencies for your modules here.
>
> For example,  
> this block includes the Android plugin for Gradle as a dependency,  
> because it provides the additional instructions  
> Gradle needs to build Android app modules.<br></br>
> #### `repositories{}` block
> The `repositories{}` block configures the repositories  
> Gradle uses to search or download the dependencies.  
> Gradle pre-configures support for remote repositories  
> such as *Google*, *Maven Central*, and *Gradle Plugin Portal**.  
> You can also use local repositories or define your own remote repositories.

## Preliminary Work<sup>🔨</sup>
* start *New empty project* by *Phone & Tablet* template with *No Activity* option.
* use the same name `composite-build-demo` for application name and its location directory.
* create package name `com.example.demo`.
* *Version Control*: make *Initial Commit*: (*GitHub*: [`~/composite-build-demo`](https://github.com/PabloXyu/composite-build-demo)).
* update `gradle.properties` to multi-module project.
* in `~/.gitignore` add lines `/.idea/name` & `/.idea/vcs.xml`, `/gradle`.
* extend `settings.gradle`, `build.gradle`, `:app:build.gradle` files to [`*.kts`](https://docs.gradle.org/current/userguide/kotlin_dsl.html#sec:scripts).
* with those *Gradle* files [migrate from *Groovy* to *Kotlin DSL*](https://developer.android.com/studio/build/migrate-to-kts).
* in `settings.gradle`add Gradle Plugin Portal repository.
* in root `build.gradle` add snippet to enable *Kotlin Source Root* for each module.
* in `app:build.gradle` update *JVM* & `compileOptions` to *Java* version *11*.
* set *compileSdk* & *targetSdk* to level *31*.
* set *Target JVM* (*IDE Kotlin Compiler* option) to version *11*.
* update `app:src\main\AndroidManifest.xml` file adding `android:fullBackupContent="@xml/app_backup_rules`.
* in `AndroidManifest` refactor *theme* to camel case (e.g. `Compositebuilddemo` to `CompositeBuildDemo`).
* add `app_backup_rules.xml` file to *App* module resources in `/src/main/res/xml`.
* ### optional: [*Markdown*](https://www.markdownguide.org/basic-syntax/) editor setup:
    * install [*Markdown Navigator Enhanced*](https://vladsch.com/product/markdown-navigator) plugin
    * go to _Main Menu_ **|** _File_ **|** _Settings…_
        * disable Markdown warning *Non-ASCII characters in an identifier*:
        * choose _Editor_ **>** _Inspections_
        * uncheck _Internationalization_ **>** _Non-ASCII characters_
        * set Markdown document icon:
            * open window _Languages & Frameworks_ **>** _Markdown_
            * in _Application Settings_ **|** _Project View_ subwindow set _Document Icon_ to _Markdown_
<br/><br/>
* add `README.md` file in root directory.
* rebuild the project.
* *Version Control*: make first Push.

> ### <sup>☑ </sup> Build Source
> `buildSrc` is a directory at the project root level which contains build info.  
> We can use this directory to enable kotlin-dsl and write logic related to custom configuration and share them across the project.  
> It was one of the most used approaches in recent days because of its testability.
> #### Gradle Doc:
> The directory `buildSrc` is treated as an included build.  
> Upon discovery of the directory, *Gradle* automatically compiles and tests this code and puts it in the classpath of your build script.  
> There can be only one buildSrc directory for multi-project builds, which has to sit in the root project directory.  
> `buildSrc` should be preferred over script plugins as it is easier to maintain, refactor, and test the code.

## Adding Build Source `buildSrc` & Build Plugin `buildPlg` modules<sup>🟦</sup>
* create directory for module `:buildSrc` (Source Build) with file `build.gradle.kts` including repos.
* in `buildSrc:build.gradle` include `kotlin-dsl` plugin and the same repos as in root build file.
* create `src/main/kotlin` directory and mark it  as *Sources Root* (blue color).
* In *Kotlin Sources Root* create `util` package with kotlin file.
* create directory for module `:buildPlg` (Source Build) with file `build.gradle` including repos.
* in `buildPlg:build.gradle` include `kotlin-dsl` plugin and the same repos as in root build file.
* create `buildPlg:settings.gradle` file with `pluginManagement{}` block including repos.
* add `includeBuild("buildPlg")` in root `:settings.gradle`.
<br/><br/>
* although [`RepositoriesMode`](https://docs.gradle.org/current/userguide/declaring_repositories.html#sub:centralized-repository-declaration) in `:settings.gradle` is marked as unstable and generates warnings, ignore it:
    * _Settings…_ **:** choose _Editor_ **>** _Inspections_
    * choose _Android_ **>** _Lint_ **>** _Correctness_
    * uncheck _Obsolete Gradle Dependency (available for Analyse|Inspect Code)_
    * _Inspect Code_, exclude warnings in `:settings.gradle`
    * this should insert `@file:Suppress("UnstableApiUsage")` at the top of the file
<br/><br/>
* rebuild the project

## Testing<sup>🧪</sup> the Build Process From the Scratch
1. delete root project directories marked orange:
    * `~/.gradle`
    * `~/build`
2. delete all module directories marked orange, if exist:
    * `~/[MODULE_NAME]/.gradle`
    * `~/[MODULE_NAME]/build`
3. In case of major compilation problems:
    * delete also `~/.idea` directory
4. Restart Android Studio IDE with cache invalidation:
    * open *Main Menu* **:** *File* **|** *Invalidate Cashes/Restart…*
5. In case of critical compilation problems:
    * delete the all directories mentioned in _**1-3**_
    * exit *Android Studio IDE*
    * delete all files in `%USERNAME%\.gradle\caches` directory (*Win10*).
    * run *Android Studio IDE*
---
## Android Gradle Plugin *7.02* release:
* build cache is removed
    * no `cleanBuildCache` task
    * no `android.enableBuildCache`  property
    * no `android.buildCacheDir`  property
* dependency configuration removed:
    * no `compile` & `…Compile` variants
    * no `provided` & `…Provided` variants
    * no `apk`
    * no `publish`
---
## Android Studio Useful Shortcut Keys
open *Main Menu* **:** *File* **:** *Settings* **|** *KeyMap*.
1. Android Studio Restart
    * choose *Main Menu* **>** *File* **>** *Invalidate Cashes/Restart…*
    * *Add Keyboard Shortcut*: `[CTRL]`+`[ALT]`+`R`
    * *Add Mouse Shortcut*: `[CTRL]`+`[Middle Click]`
2. Refactor/Rename
    * choose *Main Menu* **>** *Refactor* **>** *Rename*
    * *Add Mouse Shortcut*: `[SHIFT]`+`[Click]` (left click)
3. Show Local History
    * choose *Main Menu* **>** *File* **>** *Local History* **>** *Show History*
    * *Add Keyboard Shortcut*: `[CTRL]`+`[ALT]`+`/`
    * *Add Mouse Shortcut*: `[CTRL]`+`[DoubleClick]`
---
## Key & Mouse Shortcuts
| DEFAULT | SHORTCUT KEYS             |          ACTION            |
|:-------:|:--------------------------|:--------------------------:|
| yes     | `[CTRL]`+`[ALT]`+`S`      | Settings…                  |
| no      | `[CTRL]`+`[ALT]`+`R`      | Restart                    |
| no      | `[CTRL]`+`[Middle Click]` | Restart                    |
| yes     | `[SHIFT]`+`[ESC]`         | Hide Window                |
| yes     | `[CTRL]`+`[SHIFT]`+`/`    | Comment with Block Comment |
| yes     | `[CTRL]`+`[SHIFT]`+`J`    | Join Lines                 |
| no      | `[SHIFT]`+`[Click]`       | Refactor > Rename          |
| yes     | `[CTRL]`+`[ALT]`+`L`      | Reformat code              |
| yes     | `[CTRL]`+`[SHIFT]`+`U`    | Toggle Case                |
| yes     | `[CTRL]`+`[ALT]`+`O`      | Remove Unused Imports      |
| no      | `[CTRL]`+`[ALT]`+`/`      | Show Local History         |
| no      | `[CTRL]`+`[DoubleClick]`  | Show Local History         |

---
## How to have access to commit history with free Github
* go to your repository main page `https://github.com/[USERNAME]/[REPOSITORY_NAME]`
* add `https://github.com/[USERNAME]/[REPOSITORY_NAME]/`**`commits`**
* or `https://github.com/[USERNAME]/[REPOSITORY_NAME]/`**`commit`**
