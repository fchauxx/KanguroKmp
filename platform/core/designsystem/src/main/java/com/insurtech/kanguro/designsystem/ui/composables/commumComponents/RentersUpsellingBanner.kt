package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryLight

@Composable
fun RentersUpsellingBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    UpsellingBanner(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = TertiaryLight,
        backgroundImage = R.drawable.img_renters_upselling_background,
        frontImg = R.drawable.img_renters_upselling,
        frontImgScale = ContentScale.FillWidth,
        bannerTextId = R.string.renters_upselling_banner_text,
        bannerHighlightTextId = R.string.renters_upselling_banner_text_highlight,
        titleTextId = R.string.do_you_rent_a_place
    )
}

@Composable
@Preview
private fun RentersUpsellingBannerPreview() {
    Surface {
        RentersUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(locale = "es")
private fun RentersUpsellingBannerLocalizedPreview() {
    Surface {
        RentersUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300)
private fun RentersUpsellingBannerSmallScreenPreview() {
    Surface {
        RentersUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300, locale = "es")
private fun RentersUpsellingBannerSmallScreenLocalizedPreview() {
    Surface {
        RentersUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}
