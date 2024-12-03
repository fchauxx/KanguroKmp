package com.insurtech.kanguro.domain.model

sealed class FilePickerResult<out R> {
    data class Success<out T>(val data: T) : FilePickerResult<T>()
    data class Error(val errorType: FilePickerErrorType) : FilePickerResult<Nothing>()
}

val <T> FilePickerResult<T>.data: T?
    get() = (this as? FilePickerResult.Success)?.data
