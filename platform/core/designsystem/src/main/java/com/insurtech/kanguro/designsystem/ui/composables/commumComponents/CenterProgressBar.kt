package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CenterProgressBar(
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.circularColor
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(34.dp),
            color = color
        )
    }
}

@Preview
@Composable
private fun CenterProgressBarPreview() {
    Surface {
        CenterProgressBar()
    }
}
