package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model

sealed class TrackYourClaimsEvent {
    object OnBackButtonPressed : TrackYourClaimsEvent()
    data class OnClaimDetailPressed(val claimId: String) : TrackYourClaimsEvent()
    data class OnDirectPayToVetPressed(val claimId: String) : TrackYourClaimsEvent()
    object OnNewClaimPressed : TrackYourClaimsEvent()
    object OnPullToRefresh : TrackYourClaimsEvent()
    object OnTryAgainPressed : TrackYourClaimsEvent()
}
