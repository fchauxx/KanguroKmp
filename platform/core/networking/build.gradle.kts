plugins {
    id("kanguro.android.library")
    id("kanguro.android.hilt")
}

android {
    namespace = "com.insurtech.kanguro.networking"
}

dependencies {

    implementation(project(":common"))

    debugImplementation(libs.chucker.core)
    releaseImplementation(libs.chucker.no.op)
    api(libs.jwtdecode)
    implementation(libs.kotlin.serialization.core)
    api(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.network.response.adapter)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.core)
    api(libs.retrofit.core)
    implementation(libs.timber)
}
