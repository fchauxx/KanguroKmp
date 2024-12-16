package com.insurtech.kanguro.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform