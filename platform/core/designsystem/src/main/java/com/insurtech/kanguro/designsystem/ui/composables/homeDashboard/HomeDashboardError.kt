package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun HomeDashboardError(
    modifier: Modifier = Modifier,
    onNotificationPressed: () -> Unit = {},
    onTryAgainPressed: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize().background(color = NeutralBackground)) {
        HomeHeader(onNotificationsPressed = onNotificationPressed)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            val (loader) = createRefs()

            ErrorComponent(
                onTryAgainPressed = onTryAgainPressed,
                modifier = Modifier
                    .constrainAs(loader) {
                        linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                        linkTo(start = parent.start, end = parent.end)
                    }
            )
        }

        BottomGradientAlpha5(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
@Preview
fun HomeDashboardErrorPreview() {
    HomeDashboardError(modifier = Modifier.fillMaxSize())
}
