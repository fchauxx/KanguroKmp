package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun HomeHeader(modifier: Modifier = Modifier, showNotificationsButton: Boolean = false, onNotificationsPressed: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(painter = painterResource(id = R.drawable.ic_kanguro), contentDescription = null)

        if (showNotificationsButton) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onNotificationsPressed
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
fun HomeHeaderPreview() {
    Surface {
        HomeHeader(onNotificationsPressed = {})
    }
}
