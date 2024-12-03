package com.insurtech.kanguro.core.utils

sealed class UiState<out T> {
    sealed class Loading : UiState<Nothing>() {
        object RefreshSpinnerLoader : Loading()
        object ScreenLoader : Loading()
    }
    data class Error(val onRetry: (() -> Unit) = {}) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}
