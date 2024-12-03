package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun JavierInitTyping(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(top = 4.dp)) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.img_javier),
            contentDescription = stringResource(id = R.string.javier)
        )

        JavierMessageBox(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 24.dp
            ),
            isFirst = true
        ) {
            DotsTyping()
        }
    }
}

@Composable
@Preview
private fun JavierFirstSingleTextMessagePreview() {
    Surface {
        JavierInitTyping(
            modifier = Modifier.padding(16.dp)
        )
    }
}
