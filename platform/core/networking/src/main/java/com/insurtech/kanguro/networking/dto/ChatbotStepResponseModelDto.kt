package com.insurtech.kanguro.networking.dto

data class ChatbotStepResponseModelDto(
    val messages: List<ChatbotMessageModelDto?>?,
    val action: ActionDto?,
    val id: ChatbotStepDto?
)
