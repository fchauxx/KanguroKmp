package com.insurtech.kanguro

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    apply(from = project.rootProject.file("androidCoreVersions.gradle"))

    val buildConfig = project.extra.get("buildConfig") as LinkedHashMap<*, *>

    commonExtension.apply {
        compileSdk = buildConfig["compileSdk"] as Int

        defaultConfig {
            minSdk = buildConfig["minSdk"] as Int
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

internal fun Project.configureBuildTypes(libraryExtension: LibraryExtension) {
    libraryExtension.apply {
        defaultConfig {
            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }

                consumerProguardFiles("consumer-rules.pro")
            }
        }
    }
}
