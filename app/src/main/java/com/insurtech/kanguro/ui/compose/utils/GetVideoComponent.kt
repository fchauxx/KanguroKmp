package com.insurtech.kanguro.ui.compose.utils

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable

@Composable
fun GetVideoComponent(
    context: Context,
    showCapturePicture: Boolean = false,
    showSelectPicture: Boolean = false,
    onGetImageUri: (Uri) -> Unit
) {
    if (showCapturePicture) {
        LaunchCameraVideo(context = context) { imageUri ->
            onGetImageUri(imageUri)
        }
    }

    if (showSelectPicture) {
        LaunchSingleVideoPicker { imageUri ->
            if (imageUri != null) {
                onGetImageUri(imageUri)
            }
        }
    }
}
