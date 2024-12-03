package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.ActionLabelTextStyle
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryLight
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun ActionCardButtonWhite(
    modifier: Modifier = Modifier,
    text: String,
    iconTint: Color = SecondaryMedium,
    @DrawableRes icon: Int,
    highlightTag: String? = null,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Content(icon, iconTint, text, highlightTag)
    }
}

@Composable
private fun Content(icon: Int, iconTint: Color, text: String, label: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            colorFilter = ColorFilter.tint(iconTint)
        )
        Text(modifier = Modifier.weight(1f), text = text, style = MobaBodyBold)
        if (label != null) {
            ActionCardHighlightTag(
                label = label
            )
        }
    }
}

@Composable
private fun ActionCardHighlightTag(
    modifier: Modifier = Modifier,
    label: String
) {
    Surface(
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            modifier = modifier
                .background(TertiaryLight)
                .padding(2.dp),
            text = label,
            style = ActionLabelTextStyle
        )
    }
}

@Composable
@Preview
private fun ActionCardButtonWhitePreview() {
    Surface(
        color = NeutralBackground
    ) {
        ActionCardButtonWhite(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.what_is_covered),
            icon = R.drawable.ic_coverage_list
        )
    }
}

@Composable
@Preview
private fun ActionCardButtonWhiteLabelPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ActionCardButtonWhite(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.what_is_covered),
            icon = R.drawable.ic_coverage_list,
            highlightTag = "NEW"
        )
    }
}
