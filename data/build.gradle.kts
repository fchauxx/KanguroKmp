plugins {
    id("kanguro.android.library")
    id("kanguro.android.hilt")
}

android {
    namespace = "com.insurtech.kanguro.data"
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":platform:core:networking"))

    implementation(libs.android.ktx)

    implementation(libs.network.response.adapter)

    testImplementation(project(":platform:testing"))

    testImplementation(libs.android.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.turbine)

    testImplementation(libs.mockk)
}
