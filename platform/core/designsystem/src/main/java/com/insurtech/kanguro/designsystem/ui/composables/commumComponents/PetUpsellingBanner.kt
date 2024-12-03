package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryLight

@Composable
fun PetUpsellingBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    UpsellingBanner(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = PrimaryLight,
        backgroundImage = R.drawable.img_pet_upselling_background,
        frontImg = R.drawable.img_pet_upselling,
        frontImgScale = ContentScale.FillHeight,
        bannerTextId = R.string.pet_upselling_banner_text,
        bannerHighlightTextId = R.string.pet_upselling_banner_text_highlight,
        titleTextId = R.string.do_you_have_pet
    )
}

@Composable
@Preview
private fun PetUpsellingBannerPreview() {
    Surface {
        PetUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(locale = "es")
private fun PetUpsellingBannerLocalizedPreview() {
    Surface {
        PetUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300)
private fun PetUpsellingBannerSmallScreenPreview() {
    Surface {
        PetUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 300, locale = "es")
private fun PetUpsellingBannerSmallScreenLocalizedPreview() {
    Surface {
        PetUpsellingBanner(
            modifier = Modifier.padding(16.dp),
            onClick = {}
        )
    }
}
