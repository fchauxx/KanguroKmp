package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.getStartIndexOfWord
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest

@Composable
fun StyledCustomizableText(
    modifier: Modifier = Modifier,
    text: String,
    styledText: String,
    baseStyle: TextStyle = MobaBodyRegular,
    highlightColor: Color = TertiaryDarkest,
    highlightFontWeight: FontWeight = FontWeight.Bold,
    highlightTextDecoration: TextDecoration? = null
) {
    val styledTextIndex = text.getStartIndexOfWord(styledText)
    val subStringBefore = text.substring(startIndex = 0, endIndex = styledTextIndex + 1)
    val subStringAfter = text.substring(startIndex = styledTextIndex + styledText.length + 1)

    Text(
        modifier = modifier,
        style = baseStyle,
        text = buildAnnotatedString {
            append(subStringBefore)
            withStyle(
                style = SpanStyle(
                    fontWeight = highlightFontWeight,
                    color = highlightColor,
                    textDecoration = highlightTextDecoration
                )
            ) {
                append(styledText)
            }
            append(subStringAfter)
        }
    )
}

@Composable
@Preview
private fun StyledCustomizableText1Preview() {
    Surface {
        StyledCustomizableText(
            modifier = Modifier.padding(16.dp),
            text = "This is a test.",
            styledText = "is"
        )
    }
}

@Composable
@Preview
private fun StyledCustomizableText2Preview() {
    Surface {
        StyledCustomizableText(
            modifier = Modifier.padding(16.dp),
            text = "This is a test.",
            styledText = "test",
            highlightColor = SecondaryLight
        )
    }
}

@Composable
@Preview
private fun StyledCustomizableText3Preview() {
    Surface {
        StyledCustomizableText(
            modifier = Modifier.padding(16.dp),
            text = "Your policy is active.\nWith Kanguro you're always safe!",
            styledText = "active.",
            baseStyle = MSansSemiBoldSecondaryDarkest21,
            highlightColor = SecondaryMedium,
            highlightFontWeight = FontWeight.Black
        )
    }
}
