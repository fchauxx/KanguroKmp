package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular

@Composable
fun JavierSingleTextMessage(
    modifier: Modifier = Modifier,
    text: String,
    isFirst: Boolean = true
) {
    Row(modifier = modifier.padding(top = 4.dp)) {
        if (isFirst) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.img_javier),
                contentDescription = stringResource(id = R.string.javier)
            )
        }
        JavierMessageBox(
            modifier = Modifier.padding(
                start = if (isFirst) 8.dp else 32.dp,
                end = 24.dp
            ),
            isFirst = isFirst
        ) {
            Text(text = text, style = MobaBodyRegular)
        }
    }
}

@Composable
@Preview
private fun JavierFirstSingleTextMessagePreview() {
    Surface {
        JavierSingleTextMessage(
            modifier = Modifier.padding(16.dp),
            text = "Hi! I'm Javier and I will be helping you onboard today."
        )
    }
}

@Composable
@Preview
private fun JavierOtherSingleTextMessagePreview() {
    Surface {
        JavierSingleTextMessage(
            modifier = Modifier.padding(16.dp),
            text = "Before we start, let me ask you a few questions.",
            isFirst = false
        )
    }
}
