package com.insurtech.kanguro.shared

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ! Kotlin Multiplatform ${platform.name}"
    }
}
