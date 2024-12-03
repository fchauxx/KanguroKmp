package com.insurtech.kanguro.ui.scenes.rentersChatbot.scheduledItems

import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.model.ChatbotScheduledItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class YourScheduledItemsViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<ChatbotScheduledItem>>>(UiState.Loading.ScreenLoader)
    val uiState = _uiState.asStateFlow()

    init {
        // TODO - Replace with actual data
        _uiState.value = UiState.Success(
            listOf(
                ChatbotScheduledItem(id = "1", name = "Item 1", value = 100.toBigDecimal()),
                ChatbotScheduledItem(id = "2", name = "Item 2", value = 200.toBigDecimal()),
                ChatbotScheduledItem(id = "3", name = "Item 3", value = 300.toBigDecimal())
            )
        )
    }
}
