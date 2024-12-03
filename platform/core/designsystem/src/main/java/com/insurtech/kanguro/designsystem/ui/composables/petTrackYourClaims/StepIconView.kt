package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerStepStatus
import com.insurtech.kanguro.designsystem.ui.theme.BKSHeading4
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PositiveMedium
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.WarningMedium

@Composable
fun StepIconView(
    modifier: Modifier = Modifier,
    step: Int = 1,
    status: ClaimTrackerStepStatus = ClaimTrackerStepStatus.PENDING
) {
    val backgroundColor = when (status) {
        ClaimTrackerStepStatus.COMPLETED -> PositiveMedium
        ClaimTrackerStepStatus.IS_ALERT -> WarningMedium
        else -> NeutralBackground
    }

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .border(
                width = if (status == ClaimTrackerStepStatus.IN_PROGRESS) {
                    2.5.dp
                } else {
                    1.dp
                },
                color = if (status == ClaimTrackerStepStatus.IN_PROGRESS) {
                    PositiveMedium
                } else {
                    NeutralBackground
                },
                shape = CircleShape
            )
            .size(37.dp),
        contentAlignment = Alignment.Center
    ) {
        val textStyle = when (status) {
            ClaimTrackerStepStatus.PENDING -> BKSHeading4.copy(color = SecondaryLight)
            ClaimTrackerStepStatus.IN_PROGRESS -> BKSHeading4.copy(color = PositiveMedium)
            else -> BKSHeading4.copy(color = Color.Transparent)
        }

        when (status) {
            ClaimTrackerStepStatus.COMPLETED -> {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_claim_step_completed
                    ),
                    contentDescription = null
                )
            }
            ClaimTrackerStepStatus.IS_ALERT -> {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_claim_step_alert
                    ),
                    contentDescription = null
                )
            }
            else -> {
                Text(
                    modifier = Modifier,
                    text = "$step",
                    style = textStyle
                )
            }
        }
    }
}

@Composable
@Preview
private fun StepViewPreview() {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClaimTrackerStepStatus.values().forEach {
            StepIconView(
                modifier = Modifier,
                status = it
            )
        }
    }
}
