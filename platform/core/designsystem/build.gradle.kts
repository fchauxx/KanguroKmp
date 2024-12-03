plugins {
    id("kanguro.android.library")
    id("kanguro.android.application.compose")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
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

    implementation(
        libs.immutableCollections
    )
    ksp(libs.glide.ksp)
}
