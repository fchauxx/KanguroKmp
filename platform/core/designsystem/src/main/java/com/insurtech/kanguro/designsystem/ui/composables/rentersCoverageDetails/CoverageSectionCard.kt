package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun CoverageSectionCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, NeutralBackground),
        elevation = 2.dp
    ) {
        content()
    }
}

@Composable
@Preview
fun CoverageSectionCardPreview() {
    Surface {
        CoverageSectionCard(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(32.dp),
                text = "Some content here"
            )
        }
    }
}
