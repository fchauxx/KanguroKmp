package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R

@Composable
fun CameraPermissionDialog(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onPositivePressed: () -> Unit
) {
    BaseAlertDialog(
        modifier = modifier,
        image = R.drawable.img_camera_permission,
        title = R.string.enable_camera,
        description = R.string.enable_camera_message,
        positiveLabel = R.string.allow_access,
        onClosePressed = onClosePressed,
        onNegativePressed = onClosePressed,
        onPositivePressed = onPositivePressed
    )
}

@Composable
@Preview
private fun CameraPermissionDialogPreview() {
    CameraPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}

@Composable
@Preview(locale = "es")
private fun CameraPermissionDialogLocalizedPreview() {
    CameraPermissionDialog(onClosePressed = {}, onPositivePressed = {})
}
