package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.actions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite

@Composable
fun ActionCardLiveVet(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ActionCardButtonWhite(
        modifier = modifier,
        text = stringResource(id = R.string.action_live_vet),
        icon = R.drawable.ic_live_vet,
        onClick = { onClick() },
        highlightTag = stringResource(id = R.string.action_card_highlight_tag_new)
    )
}

@Preview
@Composable
private fun ActionCardLiveVetPreview() {
    ActionCardLiveVet {
    }
}
