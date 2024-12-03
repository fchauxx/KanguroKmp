package com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model

import com.insurtech.kanguro.designsystem.ui.composables.faq.model.QuestionModel

data class HomeDashboardFaqModel(
    val rentersFaq: List<QuestionModel> = emptyList(),
    val petFaq: List<QuestionModel> = emptyList()
) {
    val hasOnlyRentersFaq: Boolean
        get() = rentersFaq.isNotEmpty() && petFaq.isEmpty()

    val hasOnlyPetFaq: Boolean
        get() = rentersFaq.isEmpty() && petFaq.isNotEmpty()
}
