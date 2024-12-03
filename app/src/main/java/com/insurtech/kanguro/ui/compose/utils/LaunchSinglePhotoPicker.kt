package com.insurtech.kanguro.ui.compose.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.insurtech.kanguro.core.utils.isSupportedMimeType
import com.insurtech.kanguro.domain.model.FilePickerErrorType
import com.insurtech.kanguro.domain.model.FilePickerResult

@Composable
fun LaunchSinglePhotoPicker(
    onPickerResult: (FilePickerResult<Uri>) -> Unit
) {
    val contentResolver = LocalContext.current.contentResolver

    val pickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            val result = if (uri != null) {
                val mimeType = contentResolver.getType(uri).orEmpty()

                if (mimeType.isSupportedMimeType()) {
                    FilePickerResult.Success(data = uri)
                } else {
                    FilePickerResult.Error(
                        errorType = FilePickerErrorType.FormatNotSupported(
                            mimeType.substringAfterLast("/")
                        )
                    )
                }
            } else {
                FilePickerResult.Error(FilePickerErrorType.NoFileSelected)
            }

            onPickerResult(result)
        }

    SideEffect {
        pickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
}
