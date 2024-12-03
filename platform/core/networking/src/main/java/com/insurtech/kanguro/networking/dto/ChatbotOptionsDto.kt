package com.insurtech.kanguro.networking.dto

data class ChatbotOptionsDto(
    val label: String?,
    val id: String?,
    val responses: List<ChatbotMessageModelDto?>?,
    val styles: List<ChatbotStylesDto?>?
)
