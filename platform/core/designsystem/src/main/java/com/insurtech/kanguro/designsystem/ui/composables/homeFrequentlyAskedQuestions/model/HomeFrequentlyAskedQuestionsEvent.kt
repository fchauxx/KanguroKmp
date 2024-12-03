package com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model

sealed class HomeFrequentlyAskedQuestionsEvent {
    object OnPullToRefresh : HomeFrequentlyAskedQuestionsEvent()
    object OnTryAgainPressed : HomeFrequentlyAskedQuestionsEvent()
    object OnBackPressed : HomeFrequentlyAskedQuestionsEvent()
}
