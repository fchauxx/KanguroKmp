package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimFeedback
import com.insurtech.kanguro.networking.dto.ClaimFeedbackDto

object ClaimFeedbackMapper {

    fun claimFeedbackDtoToClaimFeedback(dto: ClaimFeedbackDto): ClaimFeedback =
        ClaimFeedback(
            feedbackRate = dto.feedbackRate ?: 0,
            feedbackDescription = dto.feedbackDescription
        )
}
