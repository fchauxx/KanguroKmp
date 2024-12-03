package com.insurtech.kanguro.domain.model

sealed class FilePickerErrorType {
    object PermissionDenied : FilePickerErrorType()

    data class FormatNotSupported(val format: String) : FilePickerErrorType()

    object NoFileSelected : FilePickerErrorType()
}
