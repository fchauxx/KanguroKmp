package com.insurtech.kanguro.ui.scenes.fileNotSupported

import androidx.navigation.NavController
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.domain.model.FilePickerErrorType
import com.insurtech.kanguro.domain.model.FilePickerResult

object FilePickerErrorHandler {
    fun handleFilePickerError(navController: NavController, result: FilePickerResult.Error) {
        when (val error = result.errorType) {
            is FilePickerErrorType.FormatNotSupported -> {
                navController.safeNavigate(
                    NavDashboardDirections.actionGlobalFileNotSupportedDialog(error.format)
                )
            }

            is FilePickerErrorType.PermissionDenied -> {
                navController.safeNavigate(
                    NavDashboardDirections.actionGlobalAccessFilesPermissionDialog()
                )
            }

            else -> {
                // Left empty on purpose
            }
        }
    }
}
