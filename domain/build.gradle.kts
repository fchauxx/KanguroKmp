plugins {
    id("kanguro.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.insurtech.kanguro.domain"
}

dependencies {

    api(project(":common"))

    api(libs.jwtdecode)
}
