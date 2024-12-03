package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thicknessDp: Double = 0.5,
    color: Color = NeutralLightest
) {
    Divider(
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .height(thicknessDp.dp)
    )
}
