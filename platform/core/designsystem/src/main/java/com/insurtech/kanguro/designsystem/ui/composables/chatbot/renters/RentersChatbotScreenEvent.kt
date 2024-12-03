package com.insurtech.kanguro.designsystem.ui.composables.chatbot.renters

import com.insurtech.kanguro.designsystem.toUTC
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotOptionsUi

sealed class RentersChatbotScreenEvent {
    data class OnTextMessageSent(val message: String) : RentersChatbotScreenEvent()
    data class OnDateMessageSent(val date: String) : RentersChatbotScreenEvent()
    data class OnOptionSelected(val option: ChatbotOptionsUi) : RentersChatbotScreenEvent()
    data class OnOnboardingVideoSent(val ids: List<Int>) : RentersChatbotScreenEvent()
}

fun RentersChatbotScreenEvent.getValue(): Any {
    return when (this) {
        is RentersChatbotScreenEvent.OnTextMessageSent -> {
            this.message
        }

        is RentersChatbotScreenEvent.OnDateMessageSent -> {
            this.date.toUTC()
        }

        is RentersChatbotScreenEvent.OnOptionSelected -> {
            this.option.id
        }

        is RentersChatbotScreenEvent.OnOnboardingVideoSent -> {
            this.ids
        }
    }
}

fun RentersChatbotScreenEvent.getValueUiFormatted(): String? {
    return when (this) {
        is RentersChatbotScreenEvent.OnTextMessageSent -> {
            this.message
        }

        is RentersChatbotScreenEvent.OnDateMessageSent -> {
            this.date
        }

        is RentersChatbotScreenEvent.OnOptionSelected -> {
            this.option.label
        }

        is RentersChatbotScreenEvent.OnOnboardingVideoSent -> {
            "Video Uploaded" // TODO Handle the video response to show
        }
    }
}
