package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular

@Composable
fun UserTextMessage(modifier: Modifier = Modifier, text: String) {
    Row(modifier = modifier.padding(start = 8.dp)) {
        Spacer(Modifier.weight(1f))
        UserMessageBox {
            Text(
                text = text,
                style = MobaBodyRegular
            )
        }
    }
}

@Composable
@Preview
private fun UserTextMessagePreview() {
    Surface {
        UserTextMessage(
            modifier = Modifier.padding(16.dp),
            text = "Hello"
        )
    }
}
