package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest

@Composable
fun UserMessageBox(modifier: Modifier = Modifier, content: @Composable (() -> Unit)) {
    val messageBackgroundShape =
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)

    Box(
        modifier = modifier
            .background(
                color = SecondaryLightest,
                shape = messageBackgroundShape
            )
            .padding(8.dp)
            .clip(messageBackgroundShape)
    ) {
        content()
    }
}

@Composable
@Preview
private fun UserMessageBoxPreview() {
    Surface {
        UserMessageBox(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Any content here.")
        }
    }
}
