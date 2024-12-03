package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R

@Composable
fun RightButtonClose(modifier: Modifier = Modifier, onClosePressed: () -> Unit) {
    IconButton(
        onClick = { onClosePressed() },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun RightClosePreview() {
    Surface {
        RightButtonClose(onClosePressed = {})
    }
}
