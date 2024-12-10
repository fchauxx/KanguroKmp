plugins {
    id("kanguro.android.library")
   // id("kanguro.android.application.compose")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
    id("kotlin-kapt")
  //  id("com.google.devtools.ksp")
}

android {
    namespace = "com.insurtech.kanguro.designsystem"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.android.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.core.splashscreen)
    implementation(libs.android.material)

    implementation(libs.glide.core)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.material3)
    implementation(compose.runtime)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation(libs.compose.foundation.layout)
    implementation(libs.coil.compose)
    implementation(libs.compose.flow.layout)
    implementation(
        libs.immutableCollections
    )
   // kapt(libs.glide.kapt)
    kapt("com.github.bumptech.glide:compiler:4.14.2")
}
