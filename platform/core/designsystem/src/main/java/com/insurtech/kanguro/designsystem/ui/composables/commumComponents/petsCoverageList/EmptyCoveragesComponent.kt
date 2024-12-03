package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AddCoverageButton
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun EmptyCoveragesComponent(modifier: Modifier = Modifier, onAddPetPressed: () -> Unit) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.widthIn(max = 240.dp),
            text = stringResource(id = R.string.no_policies_matching_selected_filter),
            style = MobaSubheadRegular.copy(color = SecondaryMedium)
        )

        AddCoverageButton(onAddPetPressed = onAddPetPressed)
    }
}

@Preview
@Composable
private fun EmptyCoveragesComponentPreview() {
    Surface(
        color = NeutralBackground
    ) {
        EmptyCoveragesComponent {}
    }
}
