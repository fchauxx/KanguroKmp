package com.insurtech.kanguro.ui.compose.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun LaunchSingleFilePicker(onResult: (Uri?) -> Unit) {
    val pickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            onResult(it)
        }

    SideEffect {
        pickerLauncher.launch(
            "application/pdf"
        )
    }
}
