package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun HomeDashboardLoading(modifier: Modifier = Modifier, onNotificationPressed: () -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize().background(color = NeutralBackground)) {
        ScreenLoader(color = White, modifier = Modifier.align(Alignment.Center))
        HomeHeader(onNotificationsPressed = onNotificationPressed)
        BottomGradientAlpha5(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
@Preview
fun HomeDashboardLoadingPreview() {
    HomeDashboardLoading(modifier = Modifier.fillMaxSize())
}
