package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.getStartIndexOfWord
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest

@Composable
fun StyledClickableText(
    modifier: Modifier = Modifier,
    text: String,
    styledText: String,
    baseStyle: TextStyle = MobaSubheadRegular,
    onClick: () -> Unit = {}
) {
    val styledTextIndex = text.getStartIndexOfWord(styledText)
    val subStringBefore = text.substring(startIndex = 0, endIndex = styledTextIndex + 1)
    val subStringAfter = text.substring(startIndex = styledTextIndex + styledText.length + 1)

    val annotatedString = buildAnnotatedString {
        append(subStringBefore)
        withStyle(
            style = SpanStyle(
                color = TertiaryDarkest,
                textDecoration = TextDecoration.Underline
            )
        ) {
            pushStringAnnotation(tag = styledText, annotation = styledText)
            append(styledText)
        }
        append(subStringAfter)
    }

    ClickableText(
        modifier = modifier,
        style = baseStyle,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { span ->
                    if (span.item == styledText) {
                        onClick()
                    }
                }
        }
    )
}

@Composable
@Preview
private fun StyledClickableTextPreview() {
    Surface {
        StyledClickableText(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.try_again_message),
            styledText = stringResource(id = R.string.try_again)
        )
    }
}
