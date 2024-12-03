package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest

@Composable
fun ReferAFriendBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PrimaryDarkest,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 20.dp, bottom = 24.dp)
                .fillMaxWidth(0.50f)
                .align(Alignment.TopStart),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = stringResource(id = R.string.are_you_liking_kanguro), style = MobaHeadline)
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = stringResource(id = R.string.kanguro_refer_a_friend),
                style = MobaSubheadRegular
            )
        }
        Image(
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .fillMaxWidth(0.45f)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.img_advertising),
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun AdvertisingBannerPreview() {
    Surface {
        ReferAFriendBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(locale = "es")
fun AdvertisingBannerLocalizedPreview() {
    Surface {
        ReferAFriendBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300)
fun AdvertisingBannerSmallPreview() {
    Surface {
        ReferAFriendBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300, locale = "es")
fun AdvertisingBannerSmallLocalizedPreview() {
    Surface {
        ReferAFriendBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}
