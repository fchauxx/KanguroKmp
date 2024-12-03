package com.insurtech.kanguro.domain.model

sealed class Action {

    data class Text(val type: ChatbotActionType) : Action()

    data class Date(val type: ChatbotActionType, val dateRange: ChatbotDateRange?) : Action()

    data class ScheduledItems(val type: ChatbotActionType, val policyId: String) : Action()

    data class SingleChoice(
        val type: ChatbotActionType,
        val options: List<ChatbotOptions>
    ) : Action()

    data class CameraCaptureVideo(
        val type: ChatbotActionType,
        val minimumQuantity: Int?,
        val maximumQuantity: Int?
    ) : Action()

    data class Finish(val type: ChatbotActionType) : Action()

    object Unknown : Action()
}
