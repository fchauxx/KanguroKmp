package com.insurtech.kanguro.ui.compose.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.insurtech.kanguro.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun LaunchCameraVideo(context: Context, onCameraResult: (Uri) -> Unit) {
    val uri: Uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".fileprovider",
        createVideoFile(context)
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.CaptureVideo()) {
            onCameraResult(uri)
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(uri)
            } else {
                // TODO Show message explaining the importance to the permission
            }
        }

    if (isCameraPermissionGranted(context)) {
        SideEffect {
            cameraLauncher.launch(uri)
        }
    } else {
        SideEffect {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun createVideoFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = context.externalCacheDir ?: context.filesDir
    return File.createTempFile("VIDEO_${timeStamp}_", ".mp4", storageDir)
}

private fun isCameraPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.CAMERA
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
}
