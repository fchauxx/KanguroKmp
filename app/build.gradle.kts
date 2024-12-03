import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    id("kanguro.android.application")
    id("kanguro.android.application.firebase")
    id("kanguro.android.hilt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("kanguro.android.application.compose")
    id("io.sentry.android.gradle") version "4.3.0"
}

kapt {
    correctErrorTypes = true
}

// Try to load local properties
val localProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
try {
    FileInputStream(localPropertiesFile).use { input ->
        localProperties.load(input)
    }
} catch (e: FileNotFoundException) {
    println("Could not find local.properties file. Using environment variables.\nError: $e")
}

fun findGradleProperty(property: String): String {
    try {
        return localProperties.getProperty(property) ?: System.getenv(property)
    } catch (e: NullPointerException) {
        throw Exception("Could not find property: $property.\nError: $e")
    }
}

apply(from = project.rootProject.file("androidCoreVersions.gradle"))

android {
    // API
    val devApiUrl = "https://kanguro-api-develop.azurewebsites.net/"
    val stageApiUrl = "https://kanguro-api-staging.azurewebsites.net/"
    val prodApiUrl = "https://kanguro-api.azurewebsites.net/"

    // Dev
    val devPetWebsiteUrl = "https://kanguro-pet-web-develop.azurewebsites.net"
    val devRentersWebsiteUrl = "https://kanguro-renters-web-develop.azurewebsites.net"
    val devWebsiteUrl = "https://kanguro-web-develop.azurewebsites.net"

    // Stage
    val stagePetWebsiteUrl = "https://pet.staging.kanguroseguro.com"
    val stageRentersWebsiteUrl = "https://renters.staging.kanguroseguro.com"
    val stageWebsiteUrl = "https://staging.kanguroseguro.com"

    // Prod
    val prodPetWebsiteUrl = "https://pet.kanguroseguro.com"
    val prodRentersWebsiteUrl = "https://renters.kanguroseguro.com"
    val prodWebsiteUrl = "https://kanguroseguro.com"

    // Airvet
    val airvetPartnerId = "7205b254-bb97-4279-9df2-7e908cc884b4"

    val appVersion = project.extra.get("appVersion") as LinkedHashMap<*, *>

    namespace = "com.insurtech.kanguro"
    flavorDimensions.addAll(mutableListOf("default"))

    defaultConfig {
        applicationId = "com.insurtech.kanguro"
        versionCode = appVersion["versionCode"] as Int
        versionName = appVersion["versionName"] as String
        buildConfigField("int", "SUPPORTED_BACKEND_VERSION", "7")
    }

    // Check if the buildType is a release build
    val isRelease = gradle.startParameter.taskNames.any { it.contains("release", true) }

    signingConfigs {
        if (isRelease) {
            create("release") {
                storeFile = project.rootProject.file(findGradleProperty("KEY_STORE_FILE"))
                storePassword = findGradleProperty("KEY_STORE_PASSWORD")
                keyAlias = findGradleProperty("KEY_STORE_ALIAS")
                keyPassword = findGradleProperty("KEY_STORE_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = if (isRelease) {
                signingConfigs.getByName("release")
            } else {
                null
            }
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            buildConfigField("String", "BASE_API_URL", "\"$devApiUrl\"")
            buildConfigField("String", "WEBSITE_PET_URL", "\"$devPetWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_RENTERS_URL", "\"$devRentersWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_URL", "\"$devWebsiteUrl\"")
            resValue("string", "app_name", "Kanguro Dev")
            buildConfigField("String", "KANGURO_API_KEY", "\"K13XOSJ6XVP\$QQFKjuP#\"")
            buildConfigField("String", "CI_ENVIRONMENT", "\"sandbox\"")
            buildConfigField(
                "String",
                "PLACES_SDK_API_KEY",
                "\"QUl6YVN5RFd6NE41Zmx6TDlZQ01TdE4yVTBtNTIxRTByTnlidzJV\""
            )
            buildConfigField("String", "AIRVET_PARTNER_ID", "\"$airvetPartnerId\"")
            manifestPlaceholders["SentryEnv"] = "develop"
        }
        create("stage") {
            applicationIdSuffix = ".stage"
            buildConfigField("String", "BASE_API_URL", "\"$stageApiUrl\"")
            buildConfigField("String", "WEBSITE_PET_URL", "\"$stagePetWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_RENTERS_URL", "\"$stageRentersWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_URL", "\"$stageWebsiteUrl\"")
            resValue("string", "app_name", "Kanguro Stage")
            buildConfigField("String", "KANGURO_API_KEY", "\"4fNWBr6TFAvdAoghT2cP\"")
            buildConfigField("String", "CI_ENVIRONMENT", "\"stage\"")
            buildConfigField(
                "String",
                "PLACES_SDK_API_KEY",
                "\"QUl6YVN5RFd6NE41Zmx6TDlZQ01TdE4yVTBtNTIxRTByTnlidzJV\""
            )
            buildConfigField("String", "AIRVET_PARTNER_ID", "\"$airvetPartnerId\"")
            manifestPlaceholders["SentryEnv"] = "staging"
        }
        create("prod") {
            buildConfigField("String", "BASE_API_URL", "\"$prodApiUrl\"")
            buildConfigField("String", "WEBSITE_PET_URL", "\"$prodPetWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_RENTERS_URL", "\"$prodRentersWebsiteUrl\"")
            buildConfigField("String", "WEBSITE_URL", "\"$prodWebsiteUrl\"")
            resValue("string", "app_name", "Kanguro")
            buildConfigField("String", "KANGURO_API_KEY", "\"cxXI7HGm1SC9%U*2*eXu\"")
            buildConfigField("String", "CI_ENVIRONMENT", "\"production\"")
            buildConfigField(
                "String",
                "PLACES_SDK_API_KEY",
                "\"QUl6YVN5RFd6NE41Zmx6TDlZQ01TdE4yVTBtNTIxRTByTnlidzJV\""
            )
            buildConfigField("String", "AIRVET_PARTNER_ID", "\"$airvetPartnerId\"")
            manifestPlaceholders["SentryEnv"] = "production"
        }

        lint {
            abortOnError = true
            checkDependencies = true
            checkReleaseBuilds = false
            htmlOutput = project.rootProject.file("lint-report.html")
            htmlReport = false
            lintConfig = project.rootProject.file("lint.xml")
            warningsAsErrors = true
            xmlReport = true
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":platform:analytics"))
    implementation(project(":platform:core:designsystem"))
    implementation(project(":platform:core:networking"))
    implementation(project(":platform:firebaseremoteconfig:remoteconfigdomain"))
    implementation(project(":platform:firebaseremoteconfig:remoteconfigdata"))
    implementation(project(":usecase"))

    implementation(libs.installreferrer)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    /*Base dependencies when creating new project*/
    implementation(libs.android.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.preference.ktx)
    implementation(libs.android.core.splashscreen)

    // KTX extensions
    implementation(libs.android.fragment.ktx)
    implementation(libs.android.activity)

    // MVVM, LiveData and Coroutines
    implementation(libs.android.lifecycle.extensions)
    implementation(libs.android.lifecycle.livedata.ktx)
    implementation(libs.android.lifecycle.viewmodel.ktx)
    implementation(libs.android.coroutines)

    // Navigation
    implementation(libs.android.navigation.runtime)
    implementation(libs.android.navigation.fragment)
    implementation(libs.android.navigation.ui)

    debugImplementation(libs.chucker.core)
    releaseImplementation(libs.chucker.no.op)

    // Retrofit with moshi
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.android.constraint.layout)
    implementation(libs.android.legacy.support)
    implementation(libs.junit.ext.ktx)

    // retrofit logging interceptor
    implementation(libs.okhttp.logging.interceptor)

    // Glide
    implementation(libs.glide.core)
    ksp(libs.glide.ksp)

    // OkHttp3
    implementation(libs.network.response.adapter)

    // Maps
    implementation(libs.google.play.services.maps)
    implementation(libs.google.play.services.location)

    // WebView
    implementation(libs.android.browser)

    // Logging
    implementation(libs.timber)

    // Dots for View Pager
    implementation(libs.dots.indicator)

    // Epoxy
    implementation(libs.epoxy.core)
    implementation(libs.epoxy.databinding)
    implementation(libs.epoxy.compose)
    kapt(libs.epoxy.processor)

    // Lottie
    implementation(libs.lottie.core)

    // Mask Edit Text
    implementation(libs.maskara)

    // Dependencies for local unit tests
    testImplementation(libs.junit)

    // AndroidX Test - Instrumented testing
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.android.espresso)

    // AndroidX Test - JVM testing
    testImplementation(libs.junit.ext.ktx)
    testImplementation(libs.android.test.core.ktx)
    testImplementation(libs.robolectric)

    testImplementation(libs.android.core.testing)

    // AndroidX Test - Mockk
    testImplementation(libs.mockk)

    // Use coroutine on tests
    testImplementation(libs.android.coroutines.test)

    // Dependencies for Android instrumented unit tests
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.android.coroutines.test)

    androidTestImplementation(libs.junit.ext.ktx)
    debugImplementation(libs.leak.canary)

    // Dependency for Android Signature Pad
    implementation(libs.signature.pad)

    implementation(libs.jwtdecode)

    // Play In-App Review
    implementation(libs.google.play.review.ktx)

    configurations.all {
        resolutionStrategy {
            force(libs.junit)
        }
    }
}
