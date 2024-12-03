package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R

@Composable
fun LocationPermissionDialog(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onPositivePressed: () -> Unit
) {
    BaseAlertDialog(
        modifier = modifier,
        image = R.drawable.img_location_permission,
        title = R.string.enable_location,
        description = R.string.enable_location_message,
        positiveLabel = R.string.turn_on,
        onClosePressed = onClosePressed,
        onNegativePressed = onClosePressed,
        onPositivePressed = onPositivePressed
    )
}

@Composable
@Preview
private fun LocationPermissionDialogPreview() {
    LocationPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}

@Composable
@Preview(locale = "es")
private fun LocationPermissionDialogLocalizedPreview() {
    LocationPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}
