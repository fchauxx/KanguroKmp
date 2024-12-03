package com.insurtech.kanguro.networking.dto

data class ChatbotBodyDto(
    val journey: JourneyDto?,
    val data: Map<String, Any?>
)
