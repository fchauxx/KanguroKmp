package com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model

import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel

data class PetFaqModel(
    val petFaq: List<QuestionModel> = emptyList()
)
