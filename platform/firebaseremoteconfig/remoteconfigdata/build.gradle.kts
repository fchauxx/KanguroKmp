plugins {
    id("kanguro.android.library")
    id("kanguro.android.hilt")
}

android {
    namespace = "com.insurtech.kanguro.remoteconfigdata"
}

dependencies {

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.remoteConfig)

    implementation(libs.timber)
}
