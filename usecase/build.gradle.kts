plugins {
    id("kanguro.android.library")
    id("kanguro.android.hilt")
}

android {
    namespace = "com.insurtech.kanguro.usecase"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
}
