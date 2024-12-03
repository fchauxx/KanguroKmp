package com.insurtech.kanguro.designsystem.ui.composables.rentersFrequentlyAskedQuestions.model

sealed class RentersFrequentlyAskedQuestionsEvent {
    object OnPullToRefresh : RentersFrequentlyAskedQuestionsEvent()
    object OnTryAgainPressed : RentersFrequentlyAskedQuestionsEvent()
    object OnBackPressed : RentersFrequentlyAskedQuestionsEvent()
}
