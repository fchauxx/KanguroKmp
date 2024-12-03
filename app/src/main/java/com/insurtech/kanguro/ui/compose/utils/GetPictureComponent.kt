package com.insurtech.kanguro.ui.compose.utils

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import com.insurtech.kanguro.domain.model.FilePickerResult

@Composable
fun GetPictureComponent(
    context: Context,
    showCapturePicture: Boolean = false,
    showSelectPicture: Boolean = false,
    onResult: (FilePickerResult<Uri>) -> Unit
) {
    if (showCapturePicture) {
        LaunchCameraPhoto(context = context) { imageUri ->
            onResult(FilePickerResult.Success(data = imageUri))
        }
    }

    if (showSelectPicture) {
        LaunchSinglePhotoPicker { result ->
            onResult(result)
        }
    }
}
