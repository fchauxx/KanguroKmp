package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.designsystem.ui.theme.spacingNanoLarge
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs

@Composable
fun ClaimTracker(
    modifier: Modifier = Modifier,
    claimStatus: ClaimStatus
) {
    when (claimStatus) {
        ClaimStatus.Submitted -> SubmittedState(modifier)
        ClaimStatus.InReview -> InReviewState(modifier)
        ClaimStatus.MedicalHistoryInReview -> MedicalHistoryInReviewState(modifier)
        ClaimStatus.PendingMedicalHistory -> PendingMedicalHistoryState(modifier)
        ClaimStatus.Closed, ClaimStatus.Approved, ClaimStatus.Paid -> ClosedState(modifier)
        else -> EmptyState(modifier)
    }
}

@Composable
@Preview
private fun ClaimTrackerPreview() {
    Column(
        modifier = Modifier
            .background(color = White)
            .padding(spacingXxxs),
        verticalArrangement = Arrangement.spacedBy(spacingNanoLarge)
    ) {
        ClaimTracker(
            claimStatus = ClaimStatus.Assigned
        )

        ClaimTracker(
            claimStatus = ClaimStatus.Submitted
        )

        ClaimTracker(
            claimStatus = ClaimStatus.InReview
        )

        ClaimTracker(
            claimStatus = ClaimStatus.MedicalHistoryInReview
        )

        ClaimTracker(
            claimStatus = ClaimStatus.PendingMedicalHistory
        )

        ClaimTracker(
            claimStatus = ClaimStatus.Closed
        )
    }
}
