package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDark

@Composable
fun DonationBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = TertiaryDark,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            }
    ) {
        CardContent()
    }
}

@Composable
private fun CardContent() {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_donation),
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp, top = 22.5.dp, bottom = 6.5.dp)
        )
        TextContent()
    }
}

@Composable
private fun TextContent() {
    Column {
        Text(
            text = stringResource(id = R.string.donation_banner_title),
            style = MSansSemiBoldSecondaryDarkest21,
            modifier = Modifier.padding(
                start = 18.dp,
                top = 16.dp,
                end = 16.dp
            )
        )
        Text(
            text = stringResource(id = R.string.donation_banner_subtitle),
            style = MobaSubheadRegular,
            modifier = Modifier.padding(
                start = 18.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 24.dp
            )
        )
    }
}

@Composable
@Preview
fun DonationBannerPreview() {
    Surface {
        DonationBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(locale = "es")
fun DonationBannerLocalizedPreview() {
    Surface {
        DonationBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 320)
fun DonationBannerSmallScreenPreview() {
    Surface {
        DonationBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}
