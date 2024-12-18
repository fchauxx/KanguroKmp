package com.insurtech.kanguro.shared.ui

import com.insurtech.kanguro.shared.data.AirvetUserDetails


sealed class LiveVetEvent {
    class OnDownloadPressed(
        val airvetUserDetails: AirvetUserDetails
    ) : LiveVetEvent()

    object OnClosePressed : LiveVetEvent()
    object OnTryAgainPressed : LiveVetEvent()
}
