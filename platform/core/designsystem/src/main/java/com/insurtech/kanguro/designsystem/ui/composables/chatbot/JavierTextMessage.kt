package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
fun JavierTextMessage(modifier: Modifier = Modifier, messages: List<String>) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.img_javier),
            contentDescription = stringResource(id = R.string.javier)
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(messages) { index, message ->
                JavierMessageBox(
                    isFirst = index == 0
                ) {
                    Text(text = message, style = MobaBodyRegular)
                }
            }
        }
    }
}

@Composable
@Preview
private fun JavierTextMessagePreview() {
    Surface {
        JavierTextMessage(
            modifier = Modifier.padding(16.dp),
            messages = listOf(
                "Hi! I'm Javier and I will be helping you onboard today.",
                "Before we start, let me ask you a few questions.",
                "Do you have a spouse?"
            )
        )
    }
}
