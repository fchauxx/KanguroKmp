// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    apply(from = project.file("androidCoreVersions.gradle"))


    repositories {
        google()
        mavenCentral()
    }


    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.dagger.hilt.android.gradle.plugin)
        classpath(libs.android.navigation.gradle.plugin)
        classpath(libs.google.services.plugin)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

plugins {
    alias(libs.plugins.org.jlleitschuh.gradle.ktlint)
    alias(libs.plugins.com.google.devtools.ksp) apply false
    //alias(libs.plugins.android.application) apply false
    //alias(libs.plugins.android.library) apply false
   // alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
   // id("kanguro.android.application.compose") apply false
}

tasks.register("clean", Delete::class) {
    delete(project.rootProject.buildDir)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}