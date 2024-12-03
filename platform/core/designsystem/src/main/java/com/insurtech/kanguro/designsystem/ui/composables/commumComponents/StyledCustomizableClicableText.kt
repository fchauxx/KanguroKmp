package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular

@Composable
fun StyledCustomizableClicableText(
    text: String,
    highlightText: String,
    defaultStyle: TextStyle,
    highlightColor: Color,
    highlightTextDecoration: TextDecoration? = null,
    onClick: () -> Unit
) {
    val tag = "HIGHLIGHT_CLICKABLE"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = defaultStyle.toSpanStyle()
        ) {
            append(text)
        }

        val startHighlightIndex = text.indexOf(highlightText)
        val endHighlightIndex = startHighlightIndex + highlightText.length

        if (startHighlightIndex >= 0 && endHighlightIndex <= text.length) {
            addStyle(
                style = SpanStyle(
                    color = highlightColor,
                    textDecoration = highlightTextDecoration
                ),
                start = startHighlightIndex,
                end = endHighlightIndex
            )

            addStringAnnotation(
                tag = tag,
                annotation = highlightText,
                start = startHighlightIndex,
                end = endHighlightIndex
            )
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            val annotation =
                annotatedString.getStringAnnotations(
                    tag = tag,
                    start = offset,
                    end = offset
                )
                    .firstOrNull()
            if (annotation != null) {
                onClick()
            }
        }
    )
}

@Preview
@Composable
private fun StyledCustomizableClicableTextPreview() {
    Surface {
        StyledCustomizableClicableText(
            text = "This is a test",
            highlightText = "test",
            defaultStyle = MobaSubheadRegular,
            highlightColor = Color.Blue,
            onClick = {}
        )
    }
}
