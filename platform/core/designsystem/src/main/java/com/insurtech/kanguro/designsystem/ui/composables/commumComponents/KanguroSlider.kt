package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.Track
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryExtraDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanguroSlider(
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    minValue: Float,
    maxValue: Float,
    isEnablad: Boolean = true,
    onValueChange: (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(initialValue) }

    val sliderColors = SliderDefaults.colors(
        thumbColor = TertiaryExtraDark,
        activeTickColor = Color.Cyan,
        activeTrackColor = TertiaryExtraDark,
        inactiveTrackColor = NeutralBackground
    )

    Slider(
        modifier = modifier,
        enabled = isEnablad,
        value = sliderPosition,
        onValueChange = { sliderPosition = it; onValueChange(it) },
        valueRange = minValue..maxValue,
        colors = sliderColors,
        thumb = {
            Image(
                painterResource(id = R.drawable.ic_thumb),
                "${sliderPosition.toInt()}"
            )
        },
        track = {
            Track(
                sliderState = it,
                colors = sliderColors,
                modifier = Modifier
                    .scale(scaleX = 1f, scaleY = 1.8f)
            )
        }
    )
}

@Preview
@Composable
fun KanguroSliderPreview() {
    Surface {
        KanguroSlider(
            modifier = Modifier.padding(16.dp),
            minValue = 5f,
            maxValue = 50f,
            onValueChange = {
                Log.d("slider", "$it")
            }
        )
    }
}
