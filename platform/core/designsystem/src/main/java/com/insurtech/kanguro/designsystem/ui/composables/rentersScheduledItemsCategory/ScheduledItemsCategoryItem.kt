package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest

@Composable
fun ScheduledItemsCategoryItem(
    modifier: Modifier = Modifier,
    onCategoryPressed: () -> Unit,
    text: String,
    @DrawableRes iconRes: Int
) {
    OutlinedButton(
        onClick = { onCategoryPressed() },
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryLightest),
        contentPadding = PaddingValues(
            16.dp
        )
    ) {
        ButtonContent(
            text = text,
            iconRes = iconRes,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ButtonContent(
    text: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null
    )
    Text(
        text = text,
        style = MobaCaptionRegular,
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp)
    )
    Image(
        painter = painterResource(id = R.drawable.ic_rounded_plus),
        contentDescription = null
    )
}

@Preview
@Composable
private fun ScheduledItemsCategoryItemPreview() {
    Surface {
        ScheduledItemsCategoryItem(
            modifier = Modifier.padding(16.dp),
            text = "Jewelry",
            onCategoryPressed = {},
            iconRes = R.drawable.ic_jewelry
        )
    }
}
