package com.insurtech.kanguro.domain.model

data class ChatbotMessageModel(
    val content: String,
    val sender: Sender,
    val type: ChatbotMessageType
)
