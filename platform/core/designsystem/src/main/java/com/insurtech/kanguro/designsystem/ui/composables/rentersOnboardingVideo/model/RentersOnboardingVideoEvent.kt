package com.insurtech.kanguro.designsystem.ui.composables.rentersOnboardingVideo.model

import android.net.Uri

sealed class RentersOnboardingVideoEvent {
    object OnClosePressed : RentersOnboardingVideoEvent()

    object OnCloseConfirmationPressed : RentersOnboardingVideoEvent()

    object OnBackPressed : RentersOnboardingVideoEvent()

    object OnSendPressed : RentersOnboardingVideoEvent()

    object OnDonePressed : RentersOnboardingVideoEvent()

    object OnSelectFilePressed : RentersOnboardingVideoEvent()

    object OnRecordVideoPressed : RentersOnboardingVideoEvent()

    object OnDismissBottomSheet : RentersOnboardingVideoEvent()

    object OnDeleteCapturedVideoPressed : RentersOnboardingVideoEvent()

    data class OnVideoRecorded(val videoUri: Uri) : RentersOnboardingVideoEvent()

    object OnTryAgainPressed : RentersOnboardingVideoEvent()
}
