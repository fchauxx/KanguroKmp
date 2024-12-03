package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import com.insurtech.kanguro.domain.model.PolicyDocument

sealed class RentersCoverageDetailsEvent {

    object Back : RentersCoverageDetailsEvent()

    object EditPicturePressed : RentersCoverageDetailsEvent()

    object DismissPicturePickerDialog : RentersCoverageDetailsEvent()

    object CapturePicture : RentersCoverageDetailsEvent()

    object SelectFromGallery : RentersCoverageDetailsEvent()

    object OnBillingPreferencesPressed : RentersCoverageDetailsEvent()

    object OnFileClaimPressed : RentersCoverageDetailsEvent()

    object OnEditPolicyDetailsPressed : RentersCoverageDetailsEvent()

    object OnChangeMyAddressPressed : RentersCoverageDetailsEvent()

    object OnPhonePressed : RentersCoverageDetailsEvent()

    object OnEmailPressed : RentersCoverageDetailsEvent()

    object OnFrequentlyAskedQuestionsPressed : RentersCoverageDetailsEvent()

    data class OnAdditionalCoverageInfoPressed(
        val additionalCoverageItemTypeModel: AdditionalCoverageItemTypeModel
    ) : RentersCoverageDetailsEvent()

    object OnEditAdditionalCoveragePressed : RentersCoverageDetailsEvent()

    object OnMyScheduledItemsPressed : RentersCoverageDetailsEvent()

    object OnEditAdditionalPartiesPressed : RentersCoverageDetailsEvent()

    object OnWhatIsCoveredPressed : RentersCoverageDetailsEvent()

    data class OnDocumentPressed(val document: PolicyDocument) : RentersCoverageDetailsEvent()

    object OnTryAgainPressed : RentersCoverageDetailsEvent()

    object OnPullToRefresh : RentersCoverageDetailsEvent()

    object OnFileClaimDismissed : RentersCoverageDetailsEvent()

    object OnEditPolicyDialogDismissed : RentersCoverageDetailsEvent()
}
