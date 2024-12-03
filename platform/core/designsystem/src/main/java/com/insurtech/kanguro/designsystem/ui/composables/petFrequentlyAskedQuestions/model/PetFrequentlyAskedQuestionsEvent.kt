package com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model

sealed class PetFrequentlyAskedQuestionsEvent {
    object OnPullToRefresh : PetFrequentlyAskedQuestionsEvent()
    object OnTryAgainPressed : PetFrequentlyAskedQuestionsEvent()
    object OnBackPressed : PetFrequentlyAskedQuestionsEvent()
}
