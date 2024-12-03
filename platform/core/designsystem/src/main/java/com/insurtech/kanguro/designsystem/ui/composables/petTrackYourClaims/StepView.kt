package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerStepStatus
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun StepView(
    modifier: Modifier = Modifier,
    isStart: Boolean = false,
    isEnd: Boolean = false,
    step: Int = 1,
    claimStatus: ClaimStatus,
    status: ClaimTrackerStepStatus,
    isNextPending: Boolean = false
) {
    val alignment: Alignment.Horizontal = if (isStart) {
        Alignment.Start
    } else if (isEnd) {
        Alignment.End
    } else {
        Alignment.CenterHorizontally
    }

    val weight = if (!isStart && !isEnd) {
        0.5f
    } else {
        1f
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = alignment
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isStart) {
                StepLinkView(
                    modifier = Modifier.weight(weight),
                    status = status
                )
            }

            StepIconView(
                step = step,
                status = status
            )

            if (!isEnd) {
                StepLinkView(
                    modifier = Modifier.weight(weight),
                    status = if (status == ClaimTrackerStepStatus.COMPLETED) {
                        ClaimTrackerStepStatus.COMPLETED
                    } else {
                        ClaimTrackerStepStatus.PENDING
                    },
                    isNextPending = isNextPending
                )
            }
        }

        val labelColor = when (status) {
            ClaimTrackerStepStatus.COMPLETED -> SecondaryMedium
            ClaimTrackerStepStatus.PENDING -> SecondaryLight
            ClaimTrackerStepStatus.IN_PROGRESS -> SecondaryDark
            ClaimTrackerStepStatus.IS_ALERT -> SecondaryDark
        }

        val style = if (status == ClaimTrackerStepStatus.IN_PROGRESS) {
            MobaCaptionRegular.copy(color = labelColor, fontWeight = FontWeight.Black)
        } else {
            MobaCaptionBold.copy(color = labelColor)
        }

        val textPaddingModifier = if (isStart) {
            Modifier.padding(end = 4.dp)
        } else if (isEnd) {
            Modifier.padding(start = 4.dp)
        } else {
            Modifier.padding(horizontal = 4.dp)
        }

        Text(
            modifier = textPaddingModifier,
            text = stringResource(id = claimStatus.value),
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = style,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun StepViewPreview() {
    Column(
        modifier = Modifier
            .background(color = White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StepView(
            claimStatus = ClaimStatus.Submitted,
            isStart = true,
            status = ClaimTrackerStepStatus.COMPLETED
        )

        StepView(
            claimStatus = ClaimStatus.InReview,
            status = ClaimTrackerStepStatus.IN_PROGRESS
        )

        StepView(
            claimStatus = ClaimStatus.MedicalHistoryInReview,
            status = ClaimTrackerStepStatus.IN_PROGRESS
        )

        StepView(
            claimStatus = ClaimStatus.PendingMedicalHistory,
            status = ClaimTrackerStepStatus.IS_ALERT
        )

        StepView(
            claimStatus = ClaimStatus.Closed,
            isStart = false,
            isEnd = true,
            status = ClaimTrackerStepStatus.COMPLETED
        )
    }
}
