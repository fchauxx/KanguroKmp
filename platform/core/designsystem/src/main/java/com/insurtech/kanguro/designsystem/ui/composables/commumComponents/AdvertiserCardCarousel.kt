package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.AdvertiserCardModels
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.advertiserCardAspectRatio

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdvertiserCardCarousel(
    modifier: Modifier = Modifier,
    defaultOnClickCard: (String) -> Unit,
    cardList: SnapshotStateList<AdvertiserCardModels>
) {
    // Ensures that the first page shown is cardList[0]
    val initialPage = Int.MAX_VALUE / 2 - 3

    val state = rememberPagerState(
        initialPage = initialPage,
        pageCount = { Int.MAX_VALUE }
    )

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val pageSize = screenWidth * 0.8f // 80% of screen width
    val padding = screenWidth * 0.1f // 10% of screen width
    val pageSpacing = screenWidth * 0.05f // 5% of screen width

    // Calculate the current cardList[] page
    val currentPage = state.currentPage % cardList.size

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            pageSize = PageSize.Fixed(pageSize),
            contentPadding = PaddingValues(horizontal = padding),
            beyondBoundsPageCount = cardList.size / 2,
            state = state,
            pageSpacing = pageSpacing
        ) { pagerPage ->

            val index = pagerPage % cardList.size

            val cardModel = cardList[index]

            val cardModifier = Modifier
                .fillMaxWidth()
                .aspectRatio(advertiserCardAspectRatio)

            val onClickCard = cardModel.onClick ?: defaultOnClickCard

            when (cardModel) {
                is AdvertiserCardModels.AsyncModel -> {
                    AdvertiserCard(
                        image = cardModel.image,
                        onClick = { onClickCard(cardModel.id) },
                        modifier = cardModifier
                    )
                }

                is AdvertiserCardModels.PainterModel -> {
                    AdvertiserCard(
                        image = painterResource(id = cardModel.image),
                        onClick = { onClickCard(cardModel.id) },
                        modifier = cardModifier
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        PagerIndicator(
            indicatorCount = cardList.size,
            pagerState = state,
            currentIndicator = currentPage,
            minIndicatorSize = 10.dp,
            maxIndicatorSize = 16.dp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
private fun AdvertiserCardPagerPreview(modifier: Modifier = Modifier) {
    val imageList = mutableStateListOf(
        AdvertiserCardModels.PainterModel(
            "",
            R.drawable.img_banner_advertising
        ),
        AdvertiserCardModels.AsyncModel(
            "",
            ""
        ),
        AdvertiserCardModels.PainterModel(
            "",
            R.drawable.img_banner_advertising
        ),
        AdvertiserCardModels.PainterModel(
            "",
            R.drawable.img_banner_advertising
        ),
        AdvertiserCardModels.PainterModel(
            "",
            R.drawable.img_banner_advertising
        )
    )
    Surface(
        color = NeutralBackground
    ) {
        AdvertiserCardCarousel(
            modifier = Modifier,
            cardList = imageList,
            defaultOnClickCard = { }
        )
    }
}
