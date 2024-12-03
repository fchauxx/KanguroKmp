package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest

@Composable
fun JavierChatbotHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = SecondaryLightest)
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        JavierImage()
        HeaderText()
    }
}

@Composable
private fun JavierImage() {
    Image(
        painter = painterResource(id = R.drawable.javier_blue_lighest),
        contentDescription = null
    )
}

@Composable
private fun HeaderText() {
    Column(
        modifier = Modifier.padding(start = 10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.javier_chatbot_header_title),
            style = MSansSemiBoldSecondaryDarkest21
        )
        Text(
            text = stringResource(id = R.string.javier_chatbot_header_subtitle).uppercase(),
            style = MobaCaptionBold
        )
    }
}

@Composable
@Preview
private fun JavierChatbotHeaderPreview() {
    Surface {
        JavierChatbotHeader(modifier = Modifier.padding(16.dp))
    }
}
