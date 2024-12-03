package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerStepStatus

data class ClaimStepModel(
    val weight: Float,
    val claimStatus: ClaimStatus,
    val step: Int,
    val status: ClaimTrackerStepStatus,
    val isStart: Boolean = false,
    val isEnd: Boolean = false,
    val isNextPending: Boolean = false
)

@Composable
fun ClaimState(steps: List<ClaimStepModel>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEach { step ->
            StepView(
                modifier = Modifier.weight(step.weight),
                claimStatus = step.claimStatus,
                step = step.step,
                status = step.status,
                isStart = step.isStart,
                isEnd = step.isEnd,
                isNextPending = step.isNextPending
            )
        }
    }
}

@Composable
fun SubmittedState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.COMPLETED,
                isStart = true,
                isNextPending = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.PENDING
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                step = 3,
                status = ClaimTrackerStepStatus.PENDING
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.PENDING
            )
        ),
        modifier = modifier
    )
}

@Composable
fun InReviewState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.COMPLETED,
                isStart = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.IN_PROGRESS
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                step = 3,
                status = ClaimTrackerStepStatus.PENDING
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.PENDING
            )
        ),
        modifier = modifier
    )
}

@Composable
fun MedicalHistoryInReviewState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.COMPLETED,
                isStart = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.COMPLETED
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                step = 3,
                status = ClaimTrackerStepStatus.IN_PROGRESS
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.PENDING
            )
        ),
        modifier = modifier
    )
}

@Composable
fun PendingMedicalHistoryState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.COMPLETED,
                isStart = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.COMPLETED
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.PendingMedicalHistory,
                step = 3,
                status = ClaimTrackerStepStatus.IS_ALERT
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.PENDING
            )
        ),
        modifier = modifier
    )
}

@Composable
fun ClosedState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.COMPLETED,
                isStart = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.COMPLETED
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                step = 3,
                status = ClaimTrackerStepStatus.COMPLETED
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.COMPLETED
            )
        ),
        modifier = modifier
    )
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    ClaimState(
        steps =
        listOf(
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Submitted,
                step = 1,
                status = ClaimTrackerStepStatus.PENDING,
                isStart = true
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.InReview,
                step = 2,
                status = ClaimTrackerStepStatus.PENDING
            ),
            ClaimStepModel(
                weight = 1.5f,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                step = 3,
                status = ClaimTrackerStepStatus.PENDING
            ),
            ClaimStepModel(
                weight = 1f,
                claimStatus = ClaimStatus.Closed,
                step = 4,
                isEnd = true,
                status = ClaimTrackerStepStatus.PENDING
            )
        ),
        modifier = modifier
    )
}
