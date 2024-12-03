package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest

@Composable
fun DotsTyping() {
    val numberOfDots = 3
    val dotSize = 4
    val dotColor: Color = SecondaryDarkest

    val spaceBetween = 3.dp
    val maxOffset = (dotSize * 0.75).toFloat()

    val delayUnit = 200
    val duration = numberOfDots * delayUnit

    @Composable
    fun Dot(offset: Float) {
        Spacer(
            Modifier
                .size(dotSize.dp)
                .offset(y = -offset.dp)
                .background(
                    color = dotColor,
                    shape = CircleShape
                )
        )
    }

    @Composable
    fun animateOffsetWithDelay(delay: Int) = rememberInfiniteTransition(label = "")
        .animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = duration + delayUnit
                    0f at delay
                    maxOffset at delay + (delayUnit)
                    0f at delay + delayUnit * 2
                }
            ),
            label = ""
        )

    val offsets = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        offsets.add(animateOffsetWithDelay(delay = i * delayUnit))
    }

    Row(
        modifier = Modifier
            .padding(top = maxOffset.dp)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        offsets.forEach {
            Dot(it.value)
        }
    }
}

@Composable
@Preview
private fun DotsTypingPreview() {
    Surface {
        JavierMessageBox(modifier = Modifier.padding(16.dp)) {
            DotsTyping()
        }
    }
}
