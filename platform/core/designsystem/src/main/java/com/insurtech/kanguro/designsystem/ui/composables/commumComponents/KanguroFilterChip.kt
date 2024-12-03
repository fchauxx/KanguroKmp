package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KanguroFilterChip(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    onPressed: () -> Unit,
    content: @Composable () -> Unit
) {
    val activeBorder = BorderStroke(
        width = 2.dp,
        color = SecondaryLightest
    )

    Surface(
        modifier = modifier,
        onClick = onPressed,
        shape = RoundedCornerShape(16.dp),
        border = if (isActive) activeBorder else null,
        color = White
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun KanguroFilterChipSelectedPreview() {
    Surface(
        color = NeutralBackground
    ) {
        KanguroFilterChip(
            modifier = Modifier.padding(16.dp),
            isActive = true,
            onPressed = {}
        ) {
            Text(text = "All", style = MobaCaptionRegular)
        }
    }
}

@Preview
@Composable
private fun KanguroFilterChipUnSelectedPreview() {
    Surface(
        color = NeutralBackground
    ) {
        KanguroFilterChip(
            modifier = Modifier.padding(16.dp),
            isActive = false,
            onPressed = {}
        ) {
            Text(text = "All", style = MobaCaptionRegular)
        }
    }
}
