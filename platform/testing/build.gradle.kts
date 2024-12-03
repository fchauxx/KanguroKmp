plugins {
    id("kanguro.android.library")
}

android {
    namespace = "com.insurtech.kanguro.testing"
}

dependencies {

    implementation(project(":platform:core:networking"))

    api(libs.android.test.hilt)
    implementation(libs.android.test.runner)

    implementation(libs.gson)
    implementation(libs.junit)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging.interceptor)
    api(libs.okhttp.mockWebServer)
    api(libs.test.parameter.injector)
}
