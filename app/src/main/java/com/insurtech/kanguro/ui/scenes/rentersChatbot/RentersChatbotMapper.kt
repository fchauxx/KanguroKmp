package com.insurtech.kanguro.ui.scenes.rentersChatbot

import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ActionUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotActionTypeUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotDateRangeUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotMessageModelUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotOptionsUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotStylesUi
import com.insurtech.kanguro.domain.model.Action
import com.insurtech.kanguro.domain.model.ChatbotActionType
import com.insurtech.kanguro.domain.model.ChatbotDateRange
import com.insurtech.kanguro.domain.model.ChatbotMessageModel
import com.insurtech.kanguro.domain.model.ChatbotOptions
import com.insurtech.kanguro.domain.model.ChatbotStyles
import com.insurtech.kanguro.domain.model.Sender

fun ChatbotMessageModel.toUi() = ChatbotMessageModelUi(
    this.content,
    when (this.sender) {
        Sender.User -> com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.Sender.USER
        Sender.Chatbot -> com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.Sender.CHATBOT
    }
)

fun ChatbotStyles.toUi(): ChatbotStylesUi {
    return when (this) {
        ChatbotStyles.Italic -> ChatbotStylesUi.ITALIC
        ChatbotStyles.Bold -> ChatbotStylesUi.BOLD
    }
}

fun Action.toUi(): ActionUi {
    return when (this) {
        is Action.Text -> ActionUi.Text(this.type.toUi())
        is Action.Date -> ActionUi.Date(this.type.toUi(), this.dateRange?.toUi())
        is Action.ScheduledItems -> ActionUi.ScheduledItems(this.type.toUi(), this.policyId)
        is Action.SingleChoice -> ActionUi.SingleChoice(
            this.type.toUi(),
            this.options.chatbotOptionsListToUi()
        )

        is Action.CameraCaptureVideo -> ActionUi.CameraCaptureVideo(
            this.type.toUi(),
            this.minimumQuantity,
            this.maximumQuantity
        )

        is Action.Finish -> ActionUi.Finish(this.type.toUi())
        is Action.Unknown -> ActionUi.Unknown
    }
}

fun ChatbotActionType.toUi(): ChatbotActionTypeUi {
    return when (this) {
        ChatbotActionType.Text -> ChatbotActionTypeUi.Text
        ChatbotActionType.Date -> ChatbotActionTypeUi.Date
        ChatbotActionType.ScheduledItems -> ChatbotActionTypeUi.ScheduledItems
        ChatbotActionType.SingleChoice -> ChatbotActionTypeUi.SingleChoice
        ChatbotActionType.CameraCaptureVideo -> ChatbotActionTypeUi.CameraCaptureVideo
        ChatbotActionType.Finish -> ChatbotActionTypeUi.Finish
    }
}

fun ChatbotDateRange.toUi(): ChatbotDateRangeUi {
    return ChatbotDateRangeUi(this.startDate, this.endDate)
}

fun List<ChatbotOptions>.chatbotOptionsListToUi(): List<ChatbotOptionsUi> = map {
    ChatbotOptionsUi(
        it.id,
        it.label,
        it.style?.toUi()
    )
}
