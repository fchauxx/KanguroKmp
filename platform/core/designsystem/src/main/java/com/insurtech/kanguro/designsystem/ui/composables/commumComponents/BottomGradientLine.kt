package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.designsystem.ui.theme.White20
import com.insurtech.kanguro.designsystem.ui.theme.White40
import com.insurtech.kanguro.designsystem.ui.theme.White5
import com.insurtech.kanguro.designsystem.ui.theme.White80

@Composable
fun BottomGradientLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        White5,
                        White20,
                        White40,
                        White80,
                        White
                    )
                )
            )
            .fillMaxWidth()
            .height(20.dp)
    ) {}
}

@Preview
@Composable
private fun BottomGradientLinePreview() {
    Surface {
        BottomGradientLine()
    }
}
