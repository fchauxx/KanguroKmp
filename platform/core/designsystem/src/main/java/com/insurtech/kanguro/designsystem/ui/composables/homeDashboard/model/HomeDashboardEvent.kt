package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model

import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageFilter

sealed class HomeDashboardEvent {
    object OnFileClaimPressed : HomeDashboardEvent()

    object OnPetFileClaimPressed : HomeDashboardEvent()

    object OnRentersFileClaimPressed : HomeDashboardEvent()

    object OnDismissFileAClaimTypeModal : HomeDashboardEvent()

    object OnDismissRentersFileAClaimDialog : HomeDashboardEvent()

    object OnEmailPressed : HomeDashboardEvent()

    object OnBlogPressed : HomeDashboardEvent()

    object OnFaqPressed : HomeDashboardEvent()

    data class OnRentersCoveragePressed(val policyId: String) : HomeDashboardEvent()

    object OnAddResidencePressed : HomeDashboardEvent()

    data class OnPetsCoveragePressed(val policyId: String) : HomeDashboardEvent()

    object OnAddPetsPressed : HomeDashboardEvent()

    object OnRentersUpsellingBannerPressed : HomeDashboardEvent()

    object OnPetUpsellingBannerPressed : HomeDashboardEvent()

    object OnDonationBannerPressed : HomeDashboardEvent()

    object OnNotificationsPressed : HomeDashboardEvent()

    data class OnReminderPressed(val model: ItemReminderUiModel) : HomeDashboardEvent()

    object OnSeeAllRemindersPressed : HomeDashboardEvent()

    object OnReferAFriendBannerPressed : HomeDashboardEvent()

    object OnTryAgainPressed : HomeDashboardEvent()

    object OnPullToRefresh : HomeDashboardEvent()

    object OnLiveVetPressed : HomeDashboardEvent()

    data class OnRentersCoverageFilterPressed(val filter: CoverageFilter) : HomeDashboardEvent()

    data class OnPetCoverageFilterPressed(val filter: CoverageFilter) : HomeDashboardEvent()

    object OnCloudPressed : HomeDashboardEvent()
}
