package com.insurtech.kanguro.designsystem.ui.composables.petDashboard.model

sealed class PetDashboardEvent {
    data class OnPetPressed(val petId: String) : PetDashboardEvent()
    data class OnAdvertiserPressed(val advertiserId: String) : PetDashboardEvent()
    object OnFileClaimPressed : PetDashboardEvent()
    object OnTrackClaimPressed : PetDashboardEvent()
    object OnDirectPayVetPressed : PetDashboardEvent()
    object OnAddPetPressed : PetDashboardEvent()
    object OnPaymentSettingsPressed : PetDashboardEvent()
    object OnFindVetPressed : PetDashboardEvent()
    object OnFrequentlyAskedQuestionsPressed : PetDashboardEvent()
    object PullToRefresh : PetDashboardEvent()
    object OnTellMeMorePressed : PetDashboardEvent()
    object OnTryAgainPressed : PetDashboardEvent()
    object OnLiveVetPressed : PetDashboardEvent()
}
