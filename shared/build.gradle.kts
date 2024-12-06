buildscript {

    dependencies {
      //  classpath(libs.kotlin.gradle.plugin)
    //    classpath(libs.android.gradle.plugin)
       // classpath("co.touchlab:kotlinxcodesync:0.1.5")
    }
}

/*allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/ktor")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}*/


plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("com.android.library")
}

//apply(plugin = "maven-publish")
//apply(plugin = "com.android.library")
//apply(plugin = "kotlin-android-extensions")

kotlin {
    android()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
            }
        }
        commonTest {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.insurtech.kanguro.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
