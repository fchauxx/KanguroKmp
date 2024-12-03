package com.insurtech.kanguro.networking.dto

import kotlinx.serialization.Serializable

@Serializable
sealed class ActionDto {

    @Serializable
    data class Text(val type: ChatbotActionTypeDto?) : ActionDto()

    @Serializable
    data class Date(val type: ChatbotActionTypeDto?, val dateRange: ChatbotDateRangeDto?) :
        ActionDto()

    @Serializable
    data class ScheduledItems(val type: ChatbotActionTypeDto?, val policyId: String?) : ActionDto()

    @Serializable
    data class SingleChoice(
        val type: ChatbotActionTypeDto?,
        val options: List<ChatbotOptionsDto?>?
    ) : ActionDto()

    @Serializable
    data class CameraCaptureVideo(
        val type: ChatbotActionTypeDto?,
        val minimunQuantity: Int?,
        val maximumQuantity: Int?
    ) : ActionDto()

    @Serializable
    data class Finish(val type: ChatbotActionTypeDto?) : ActionDto()

    object Unknown : ActionDto()

    companion object {
        val labelKey = "type"
    }
}
