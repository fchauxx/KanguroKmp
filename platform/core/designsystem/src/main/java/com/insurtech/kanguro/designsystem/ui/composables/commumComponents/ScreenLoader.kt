package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun ScreenLoader(modifier: Modifier = Modifier, color: Color = NeutralBackground) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        ),
        label = ""
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = angle
                },
            painter = painterResource(id = R.drawable.ic_loading),
            colorFilter = ColorFilter.tint(color),
            contentDescription = "Loading"
        )
    }
}

@Composable
@Preview
fun ScreenLoaderPreview() {
    Surface {
        ScreenLoader(modifier = Modifier.padding(16.dp))
    }
}

@Composable
@Preview
fun ScreenLoaderFullSizePreview() {
    ScreenLoader(modifier = Modifier.fillMaxSize())
}

@Composable
@Preview
fun ScreenLoaderFullSizeFilledPreview() {
    ScreenLoader(
        color = White,
        modifier = Modifier
            .background(color = NeutralBackground)
            .fillMaxSize()
    )
}
