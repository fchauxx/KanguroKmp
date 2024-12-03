package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3Light
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest

@Composable
fun StyledText(
    modifier: Modifier = Modifier,
    text: String,
    highlightColor: Color = TertiaryDarkest,
    styledText: String
) {
    StyledCustomizableText(
        modifier = modifier,
        text = text,
        styledText = styledText,
        baseStyle = MobaTitle3Light.copy(color = SecondaryDark),
        highlightColor = highlightColor,
        highlightFontWeight = FontWeight.W600
    )
}

@Composable
@Preview
private fun StyledText1Preview() {
    Surface {
        StyledText(
            modifier = Modifier.padding(16.dp),
            text = "This is a test.",
            styledText = "is"
        )
    }
}

@Composable
@Preview
private fun StyledText2Preview() {
    Surface {
        StyledText(
            modifier = Modifier.padding(16.dp),
            text = "This is a test.",
            styledText = "test"
        )
    }
}

@Composable
@Preview
private fun StyledText3Preview() {
    Surface {
        StyledText(
            modifier = Modifier.padding(16.dp),
            text = "Your policy is active.\nWith Kanguro you're always safe!",
            styledText = "active."
        )
    }
}
