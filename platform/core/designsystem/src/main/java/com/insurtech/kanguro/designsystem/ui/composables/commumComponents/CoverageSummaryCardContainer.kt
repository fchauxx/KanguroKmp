package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoverageSummaryCardContainer(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.width(157.dp),
        onClick = onPressed,
        shape = RoundedCornerShape(16.dp),
        border = border,
        color = White,
        elevation = 2.dp
    ) {
        content()
    }
}

@Composable
@Preview
fun CoverageSummaryCardContainerPreview() {
    Surface {
        CoverageSummaryCardContainer(
            modifier = Modifier.padding(16.dp),
            onPressed = { }
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "This is the content of the card"
            )
        }
    }
}
