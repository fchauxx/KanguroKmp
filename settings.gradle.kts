gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

include(":remoteconfigdata")
//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "com.android.library") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
rootProject.name = "Kanguro"
include(":app")
include(":platform:core:designsystem")
include(":platform:analytics")
include(":platform:testing")
include(":platform:core:networking")
include(":common")
include(":data")
include(":domain")
include(":platform:firebaseremoteconfig:remoteconfigdata")
include(":platform:firebaseremoteconfig:remoteconfigdomain")
include(":usecase")
include(":shared")
