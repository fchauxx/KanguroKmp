plugins {
    id("kanguro.android.library")
    id("kanguro.android.hilt")
}

android {
    namespace = "com.insurtech.kanguro.analytics"
}

dependencies {

    implementation(libs.android.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.android.espresso)
}
