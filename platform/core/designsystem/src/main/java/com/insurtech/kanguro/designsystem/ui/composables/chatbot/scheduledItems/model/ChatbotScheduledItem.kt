package com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.math.BigDecimal

data class ChatbotScheduledItem(
    val id: String,
    val name: String,
    val value: BigDecimal
) {
    var selected by mutableStateOf(false)
}
