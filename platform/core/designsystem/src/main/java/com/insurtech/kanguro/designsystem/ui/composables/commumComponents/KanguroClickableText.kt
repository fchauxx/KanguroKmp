package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.ClickableTextStyle

@Composable
fun KanguroClickableText(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(text),
        style = ClickableTextStyle,
        onClick = {
            onClick()
        }
    )
}

@Composable
@Preview
private fun KanguroClickableTextPreview() {
    Surface {
        KanguroClickableText(
            modifier = Modifier.padding(16.dp),
            text = "+ Add to your policy for \$ 3.99/mo",
            onClick = {}
        )
    }
}
