gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

include(":remoteconfigdata")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
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
