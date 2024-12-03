package com.insurtech.kanguro.designsystem.ui.composables.rentersFrequentlyAskedQuestions.model

import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel

data class RentersFaqModel(
    val rentersFaq: List<QuestionModel> = emptyList()
)
