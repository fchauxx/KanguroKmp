plugins {
     kotlin("multiplatform")
     kotlin("plugin.compose")
    // alias(libs.plugins.kotlinMultiplatform)
    id("com.android.library")
    id("org.jetbrains.compose")
}

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
               implementation(compose.foundation)
               implementation(compose.material3)
               implementation(compose.runtime)
            }
        }
        commonTest {
            dependencies {
            }
        }
        iosMain{
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
