package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.BKSHeading4

@Composable
fun EditCoverageDetailSectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    contentDescription: String? = null,
    onInformationPressed: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = BKSHeading4,
            modifier = Modifier
        )

        Image(
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable {
                    onInformationPressed()
                },
            painter = painterResource(R.drawable.ic_endorsement_info_circle),
            contentDescription = contentDescription
        )
    }
}

@Composable
@Preview
private fun EditCoverageDetailSectionTitlePreview() {
    Surface {
        EditCoverageDetailSectionTitle(
            modifier = Modifier.padding(16.dp),
            title = "Your Liability",
            onInformationPressed = {}
        )
    }
}
