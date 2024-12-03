package com.insurtech.kanguro.domain.model

data class ClaimFeedbackBody(
    val feedbackRate: Int,
    val feedbackDescription: String?
)
