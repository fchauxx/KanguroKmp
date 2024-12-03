package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.HomeDashboardEvent

@Composable
fun MoreActionsSection(
    modifier: Modifier = Modifier,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        UpperCaseCaseSectionTitle(
            title = R.string.more_actions
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActionCardButtonWhite(
            text = stringResource(id = R.string.file_claim),
            icon = R.drawable.ic_add_square
        ) {
            onEvent(HomeDashboardEvent.OnFileClaimPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.cloud),
            icon = R.drawable.ic_home_cloud
        ) {
            onEvent(HomeDashboardEvent.OnCloudPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.frequently_asked_questions),
            icon = R.drawable.ic_message_question
        ) {
            onEvent(HomeDashboardEvent.OnFaqPressed)
        }
    }
}

@Preview
@Composable
private fun MoreActionsSectionsPreview() {
    Surface {
        MoreActionsSection(
            onEvent = {}
        )
    }
}
