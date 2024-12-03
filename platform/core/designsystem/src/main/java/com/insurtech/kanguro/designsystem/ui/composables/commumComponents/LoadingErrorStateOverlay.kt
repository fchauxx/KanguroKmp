package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun LoadingErrorStateOverlay(
    isLoading: Boolean,
    isError: Boolean,
    loaderColor: Color = White,
    onTryAgainPressed: () -> Unit
) {
    if (isLoading) {
        Loader(loaderColor = loaderColor)
    } else if (isError) {
        Error {
            onTryAgainPressed()
        }
    }
}

@Composable
private fun Loader(
    loaderColor: Color
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (loader) = createRefs()

        ScreenLoader(
            color = loaderColor,
            modifier = Modifier
                .size(width = 87.dp, height = 84.dp)
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.45f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Composable
private fun Error(
    onTryAgainPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
    ) {
        val (errorComponent) = createRefs()

        ErrorComponent(
            onTryAgainPressed = onTryAgainPressed,
            modifier = Modifier
                .constrainAs(errorComponent) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Preview
@Composable
private fun LoadingErrorStateOverlayLoading() {
    Surface(
        color = NeutralBackground
    ) {
        LoadingErrorStateOverlay(
            isLoading = true,
            isError = false,
            onTryAgainPressed = { }
        )
    }
}

@Preview
@Composable
private fun LoadingErrorStateOverlayError() {
    Surface(
        color = NeutralBackground
    ) {
        LoadingErrorStateOverlay(
            isLoading = false,
            isError = true,
            onTryAgainPressed = { }
        )
    }
}
