package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun ActionCardButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    iconTint: Color? = null,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = NeutralBackground),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Content(icon, iconTint, text)
    }
}

@Composable
private fun Content(
    @DrawableRes icon: Int,
    iconTint: Color? = null,
    text: String
) {
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
            colorFilter = if (iconTint != null) {
                ColorFilter.tint(iconTint)
            } else {
                null
            }
        )
        Text(text = text, style = MobaBodyBold)
    }
}

@Composable
@Preview
private fun ActionCardButtonPreview() {
    Surface {
        ActionCardButton(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.what_is_covered),
            icon = R.drawable.ic_coverage_list
        )
    }
}
