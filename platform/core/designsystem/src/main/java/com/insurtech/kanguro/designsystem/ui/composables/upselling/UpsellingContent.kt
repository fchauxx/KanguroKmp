package com.insurtech.kanguro.designsystem.ui.composables.upselling

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledCustomizableText
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun UpsellingContent(
    imageId: Int,
    titleId: Int,
    upperTextId: Int,
    upperTextHighlightId: Int,
    bottomTextId: Int,
    bottomTextHighlightId: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(id = titleId),
            style = MobaTitle3SemiBold.copy(textAlign = TextAlign.Center)
        )

        Spacer(modifier = Modifier.height(24.dp))

        StyledCustomizableText(
            text = stringResource(id = upperTextId),
            styledText = stringResource(id = upperTextHighlightId),
            baseStyle = MobaBodyRegular.copy(
                color = SecondaryDark,
                textAlign = TextAlign.Center
            ),
            highlightFontWeight = FontWeight.Bold,
            highlightColor = SecondaryDark
        )

        Spacer(modifier = Modifier.height(24.dp))

        StyledCustomizableText(
            text = stringResource(id = bottomTextId),
            styledText = stringResource(id = bottomTextHighlightId),
            baseStyle = MobaBodyRegular.copy(
                color = SecondaryDark,
                textAlign = TextAlign.Center
            ),
            highlightFontWeight = FontWeight.Bold,
            highlightColor = SecondaryDark
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.upselling_quote_now),
            style = MobaTitle3SemiBold.copy(textAlign = TextAlign.Center)
        )
    }
}

@Preview
@Composable
private fun UpsellingContentPreview() {
    Surface {
        UpsellingContent(
            imageId = R.drawable.img_renters_upselling_main,
            titleId = R.string.renters_upselling_do_you_rent_a_place,
            upperTextId = R.string.renters_upselling_content_upper_text,
            upperTextHighlightId = R.string.renters_upselling_content_upper_text_highlighted,
            bottomTextId = R.string.renters_upselling_content_bottom_text,
            bottomTextHighlightId = R.string.renters_upselling_content_bottom_text_highlighted
        )
    }
}
