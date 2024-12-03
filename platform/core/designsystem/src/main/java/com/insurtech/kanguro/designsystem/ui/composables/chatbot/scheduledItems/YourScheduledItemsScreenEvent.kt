package com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems

import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.model.ChatbotScheduledItem

sealed class YourScheduledItemsScreenEvent {
    object OnClosePressed : YourScheduledItemsScreenEvent()
    object OnDonePressed : YourScheduledItemsScreenEvent()
    object OnTryAgainPressed : YourScheduledItemsScreenEvent()
    data class OnItemPressed(val item: ChatbotScheduledItem) : YourScheduledItemsScreenEvent()
}
