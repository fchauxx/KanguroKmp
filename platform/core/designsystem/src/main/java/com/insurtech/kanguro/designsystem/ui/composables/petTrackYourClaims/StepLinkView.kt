package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerStepStatus
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PositiveMedium
import com.insurtech.kanguro.designsystem.ui.theme.WarningMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun StepLinkView(
    modifier: Modifier = Modifier,
    status: ClaimTrackerStepStatus,
    isNextPending: Boolean = false
) {
    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .height(height = 8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val warningBrush = Brush.horizontalGradient(
            listOf(
                PositiveMedium,
                WarningMedium,
                WarningMedium
            )
        )

        val innerBackgroundColor = when (status) {
            ClaimTrackerStepStatus.PENDING -> NeutralBackground
            ClaimTrackerStepStatus.IN_PROGRESS -> PositiveMedium
            ClaimTrackerStepStatus.COMPLETED -> PositiveMedium
            ClaimTrackerStepStatus.IS_ALERT -> WarningMedium
        }

        val localModifier = if (status == ClaimTrackerStepStatus.IS_ALERT) {
            Modifier
                .background(brush = warningBrush)
        } else {
            Modifier
                .background(
                    color = innerBackgroundColor,
                    shape = if (isNextPending) {
                        RoundedCornerShape(0.dp, 3.dp, 3.dp, 0.dp)
                    } else {
                        RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                    }
                )
        }

        Box(
            modifier = localModifier
                .height(height = 6.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
private fun StepIconViewPreview() {
    Column(
        modifier = Modifier.background(color = White),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClaimTrackerStepStatus.values().forEach {
            StepLinkView(
                status = it
            )
        }
    }
}
