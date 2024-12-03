package com.insurtech.kanguro.domain.model

data class ChatbotStepModel(
    val messages: List<ChatbotMessageModel>,
    val action: Action,
    val id: ChatbotStep
)
