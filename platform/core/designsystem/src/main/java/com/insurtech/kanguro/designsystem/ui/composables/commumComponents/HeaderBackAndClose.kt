package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeaderBackAndClose(
    modifier: Modifier = Modifier,
    onBackButtonPressed: (() -> Unit)? = null,
    onClosePressed: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackButtonPressed != null) {
            BackButton(onBackPressed = onBackButtonPressed)
        }
        RightButtonClose(
            onClosePressed = onClosePressed
        )
    }
}

@Composable
@Preview
private fun HeaderBackAndClosePreview() {
    Surface {
        HeaderBackAndClose(
            modifier = Modifier.padding(16.dp),
            onBackButtonPressed = {},
            onClosePressed = {}
        )
    }
}

@Composable
@Preview
private fun HeaderBackAndCloseNoBackPreview() {
    Surface {
        HeaderBackAndClose(
            modifier = Modifier.padding(16.dp),
            onClosePressed = {}
        )
    }
}
