package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.DarkBackground5Alpha

@Composable
fun BottomGradientAlpha5(
    modifier: Modifier
) {
    val colorStops = arrayOf(
        0.0f to Color.Transparent,
        1f to DarkBackground5Alpha
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(
                brush = Brush.verticalGradient(colorStops = colorStops),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
    )
}

@Preview
@Composable
private fun BottomGradientAlpha5Preview() {
    Surface {
        BottomGradientAlpha5(modifier = Modifier.padding(16.dp))
    }
}
