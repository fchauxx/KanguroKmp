package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    indicatorCount: Int,
    pagerState: PagerState,
    currentIndicator: Int,
    minIndicatorSize: Dp,
    maxIndicatorSize: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(maxIndicatorSize)
    ) {
        repeat(indicatorCount) { iteration ->

            val size =
                lerp(maxIndicatorSize, minIndicatorSize, pagerState.currentPageOffsetFraction.absoluteValue * 2)

            val color = if (currentIndicator == iteration) PrimaryDarkest else Color.LightGray

            Box(
                modifier = Modifier
                    .size(
                        if (currentIndicator == iteration) size else minIndicatorSize
                    )
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PagerIndicatorPreview() {
    Surface {
        val state = rememberPagerState {
            4
        }
        PagerIndicator(
            indicatorCount = state.pageCount,
            pagerState = state,
            currentIndicator = state.currentPage,
            minIndicatorSize = 10.dp,
            maxIndicatorSize = 16.dp
        )
    }
}
