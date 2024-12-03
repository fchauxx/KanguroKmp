package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.White
import kotlin.math.min
import com.insurtech.kanguro.designsystem.dp as pixelToDp

@Composable
fun KanguroMotionLayoutContainer(
    @DrawableRes image: Int,
    isLoading: Boolean,
    isError: Boolean,
    onBackPressed: () -> Unit,
    onTryAgainPressed: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val scroll = rememberScrollState(0)
    var propertyInformationHeight by remember { mutableIntStateOf(0) }
    val initialHeaderPositionRatio = 250f
    val finalHeaderPositionRatio = 130f
    val scrollAmplificationFactor = 1f
    val animationEndPoint = 1f
    val headerStart = screenHeight * (initialHeaderPositionRatio / finalHeaderPositionRatio)

    val progress = min(
        scroll.value / (scrollAmplificationFactor * (headerStart - screenHeight)),
        animationEndPoint
    )

    if (isLoading) {
        LoaderState(
            modifier = Modifier
                .padding(top = 194.dp + propertyInformationHeight.pixelToDp.dp)
                .fillMaxSize()
        )
    } else if (isError) {
        ErrorState(
            modifier = Modifier
                .padding(top = 100.dp + propertyInformationHeight.pixelToDp.dp)
                .fillMaxSize(),
            onTryAgainPressed = onTryAgainPressed
        )
    } else {
        MainContent(scroll, propertyInformationHeight, content)
    }

    HeaderSection(
        progress = progress,
        image = image,
        onBackPressed = onBackPressed
    ) {
        propertyInformationHeight = it
    }
}

@Composable
private fun MainContent(
    scroll: ScrollState,
    propertyInformationHeight: Int,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scroll)
            .padding(horizontal = 24.dp)
            .background(color = White)
    ) {
        Spacer(Modifier.height(230.dp + propertyInformationHeight.pixelToDp.dp))
        content()
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    onBackPressed: () -> Unit = {},
    progress: Float,
    onHeightChanged: (Int) -> Unit
) {
    val context = LocalContext.current

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.renters_screen_content_motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        modifier = modifier.fillMaxSize(),
        motionScene = MotionScene(content = motionScene),
        progress = progress
    ) {
        Box(
            modifier = Modifier.layoutId("header")
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(id = image),
                contentDescription = null
            )

            IconButton(
                modifier = Modifier
                    .padding(top = 52.dp, bottom = 0.dp)
                    .padding(horizontal = 16.dp),
                onClick = onBackPressed
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_return),
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .layoutId("spacer")
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            Spacer(
                modifier = Modifier
                    .height(34.dp)
                    .onSizeChanged {
                        onHeightChanged(it.height)
                    }
            )
        }
    }
}

@Composable
private fun LoaderState(modifier: Modifier) {
    ScreenLoader(modifier = modifier)
}

@Composable
private fun ErrorState(
    modifier: Modifier,
    onTryAgainPressed: () -> Unit
) {
    ErrorComponent(modifier = modifier, onTryAgainPressed = onTryAgainPressed)
}

@Composable
@Preview
private fun KanguroMotionLayoutContainerPreview() {
    Surface {
        KanguroMotionLayoutContainer(
            image = R.drawable.img_home_faq_banner,
            isLoading = false,
            isError = false,
            onBackPressed = {},
            onTryAgainPressed = {},
            content = {
                Column {
                    Text(
                        text = "Any Content Here"
                    )
                }
            }
        )
    }
}

@Composable
@Preview
private fun KanguroMotionLayoutContainerErrorPreview() {
    KanguroMotionLayoutContainer(
        image = R.drawable.img_home_faq_banner,
        isLoading = false,
        isError = true,
        onBackPressed = {},
        onTryAgainPressed = {}
    )
}

@Composable
@Preview
private fun KanguroMotionLayoutContainerLoadingPreview() {
    KanguroMotionLayoutContainer(
        image = R.drawable.img_home_faq_banner,
        isLoading = true,
        isError = false,
        onBackPressed = {},
        onTryAgainPressed = {}
    )
}
