package com.insurtech.kanguro.designsystem.ui.composables.liveVet.model

import com.insurtech.kanguro.domain.model.AirvetUserDetails

sealed class LiveVetEvent {
    class OnDownloadPressed(
        val airvetUserDetails: AirvetUserDetails
    ) : LiveVetEvent()

    object OnClosePressed : LiveVetEvent()
    object OnTryAgainPressed : LiveVetEvent()
}
