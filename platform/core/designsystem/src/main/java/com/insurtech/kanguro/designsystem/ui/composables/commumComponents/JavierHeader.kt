package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21

@Composable
fun JavierHeader(title: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.javier_blue_darkest),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MSansSemiBoldSecondaryDarkest21
        )
    }
}

@Composable
@Preview
private fun JavierHeaderPreview() {
    Surface {
        JavierHeader(
            title = stringResource(id = R.string.scheduled_items_title)
        )
    }
}
