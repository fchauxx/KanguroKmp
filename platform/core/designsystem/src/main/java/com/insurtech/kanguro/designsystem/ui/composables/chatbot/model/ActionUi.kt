package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

sealed class ActionUi {

    data class Text(val type: ChatbotActionTypeUi) : ActionUi()

    data class Date(
        val type: ChatbotActionTypeUi,
        val dateRange: ChatbotDateRangeUi?
    ) : ActionUi()

    data class ScheduledItems(val type: ChatbotActionTypeUi, val policyId: String) : ActionUi()

    data class SingleChoice(
        val type: ChatbotActionTypeUi,
        val options: List<ChatbotOptionsUi>
    ) : ActionUi()

    data class CameraCaptureVideo(
        val type: ChatbotActionTypeUi,
        val minimunQuantity: Int?,
        val maximumQuantity: Int?
    ) : ActionUi() // Action to navigate to the OnboardingVideo Screen

    data class Finish(val type: ChatbotActionTypeUi) : ActionUi()

    object Unknown : ActionUi()
}
