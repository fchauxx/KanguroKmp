package com.insurtech.kanguro.networking.dto

data class ChatbotMessageModelDto(
    val content: String?,
    val sender: SenderDto?,
    val type: ChatbotMessageTypeDto?
)
