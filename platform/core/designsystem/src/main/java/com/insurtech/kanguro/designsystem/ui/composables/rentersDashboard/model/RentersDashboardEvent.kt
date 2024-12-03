package com.insurtech.kanguro.designsystem.ui.composables.rentersDashboard.model

sealed class RentersDashboardEvent {
    data class OnCoveragePressed(val coverageId: String) : RentersDashboardEvent()
    object OnAddResidencePressed : RentersDashboardEvent()
    object OnDonationPressed : RentersDashboardEvent()
    object OnFileClaimPressed : RentersDashboardEvent()
    object OnPaymentSettingsPressed : RentersDashboardEvent()
    object OnFrequentlyAskedQuestionsPressed : RentersDashboardEvent()
    object OnTryAgainPressed : RentersDashboardEvent()
    object PullToRefresh : RentersDashboardEvent()
    object OnTellMeMorePressed : RentersDashboardEvent()
    object OnEmailPressed : RentersDashboardEvent()
    object OnFileClaimDismissed : RentersDashboardEvent()
}
