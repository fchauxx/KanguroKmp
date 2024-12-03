package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.advertiserCardAspectRatio
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertiserCard(
    image: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        onClick = onClick
    ) {
        SubcomposeAsyncImage(
            model = image,
            loading = {
                ScreenLoader(
                    modifier = Modifier
                        .padding(vertical = spacingXxs)
                        .fillMaxWidth()
                )
            },
            contentScale = ContentScale.FillWidth,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvertiserCard(
    image: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        onClick = onClick
    ) {
        Image(
            painter = image,
            contentScale = ContentScale.FillWidth,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun AdvertiserCardPainterPreview() {
    AdvertiserCard(
        image = painterResource(id = R.drawable.img_banner_advertising),
        onClick = { },
        modifier = Modifier.width(300.dp).aspectRatio(advertiserCardAspectRatio)
    )
}

@Preview
@Composable
private fun AdvertiserCardAsyncPreview() {
    AdvertiserCard(
        image = "",
        onClick = { },
        modifier = Modifier.width(300.dp).aspectRatio(advertiserCardAspectRatio)
    )
}
