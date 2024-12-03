package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R

@Composable
fun NotificationPermissionDialog(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onPositivePressed: () -> Unit
) {
    BaseAlertDialog(
        modifier = modifier,
        image = R.drawable.img_notification_permission,
        title = R.string.enable_notifications,
        description = R.string.enable_notifications_message,
        positiveLabel = R.string.turn_on,
        onClosePressed = onClosePressed,
        onNegativePressed = onClosePressed,
        onPositivePressed = onPositivePressed
    )
}

@Composable
@Preview
private fun NotificationPermissionDialogPreview() {
    NotificationPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}

@Composable
@Preview(locale = "es")
private fun NotificationPermissionDialogLocalizedPreview() {
    NotificationPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}
