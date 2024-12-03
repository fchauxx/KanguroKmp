package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryLight
import com.insurtech.kanguro.designsystem.dp as pixelToDp

@Composable
fun UpsellingBanner(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    @DrawableRes backgroundImage: Int,
    @DrawableRes frontImg: Int,
    frontImgScale: ContentScale,
    @StringRes bannerTextId: Int,
    @StringRes bannerHighlightTextId: Int,
    @StringRes titleTextId: Int,
    onClick: () -> Unit
) {
    var imageWith by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .heightIn(min = 136.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.50f)
                .onSizeChanged {
                    imageWith = it.width
                }
                .align(Alignment.BottomEnd),
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.BottomEnd
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(0.50f)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = frontImg),
            contentDescription = null,
            contentScale = frontImgScale,
            alignment = Alignment.BottomEnd
        )

        CardContent(
            modifier = Modifier
                .align(Alignment.CenterStart),
            titleTextId = titleTextId,
            bannerTextId = bannerTextId,
            bannerHighlightTextId = bannerHighlightTextId,
            imageWith = imageWith
        )
    }
}

@Composable
private fun CardContent(
    modifier: Modifier,
    titleTextId: Int,
    bannerTextId: Int,
    bannerHighlightTextId: Int,
    imageWith: Int
) {
    Column(
        modifier = modifier
            .padding(
                start = 16.dp,
                top = 13.66.dp,
                bottom = 13.66.dp,
                end = imageWith.pixelToDp.dp
            )
    ) {
        Text(
            text = stringResource(id = titleTextId),
            style = MSansSemiBoldSecondaryDarkest21
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledCustomizableText(
            text = stringResource(id = bannerTextId),
            styledText = stringResource(id = bannerHighlightTextId),
            baseStyle = MobaSubheadRegular,
            highlightFontWeight = FontWeight.Bold,
            highlightColor = SecondaryDarkest
        )
    }
}

@Composable
@Preview
private fun RentersUpsellingBannerPreview() {
    UpsellingBanner(
        modifier = Modifier.padding(16.dp),
        onClick = { },
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
private fun PetUpsellingBannerPreview() {
    UpsellingBanner(
        modifier = Modifier.padding(16.dp),
        onClick = { },
        backgroundColor = PrimaryLight,
        backgroundImage = R.drawable.img_pet_upselling_background,
        frontImg = R.drawable.img_pet_upselling,
        frontImgScale = ContentScale.FillHeight,
        bannerTextId = R.string.pet_upselling_banner_text,
        bannerHighlightTextId = R.string.pet_upselling_banner_text_highlight,
        titleTextId = R.string.do_you_have_pet
    )
}
