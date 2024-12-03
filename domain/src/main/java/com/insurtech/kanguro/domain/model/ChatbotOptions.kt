package com.insurtech.kanguro.domain.model

data class ChatbotOptions(
    val label: String,
    val id: String,
    val responses: List<ChatbotMessageModel>,
    val style: ChatbotStyles?
)
