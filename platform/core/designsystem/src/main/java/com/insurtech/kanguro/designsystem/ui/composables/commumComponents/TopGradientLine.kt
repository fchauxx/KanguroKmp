package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.designsystem.ui.theme.White20
import com.insurtech.kanguro.designsystem.ui.theme.White40
import com.insurtech.kanguro.designsystem.ui.theme.White5
import com.insurtech.kanguro.designsystem.ui.theme.White80

@Composable
fun TopGradientLine(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        White,
        White80,
        White40,
        White20,
        White5
    )
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = colors
                )
            )
            .fillMaxWidth()
            .height(24.dp)
    ) {}
}

@Preview
@Composable
private fun TopGradientLinePreview() {
    Surface {
        TopGradientLine()
    }
}
