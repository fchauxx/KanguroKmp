package com.insurtech.kanguro.core.base.errorHandling

import androidx.annotation.Keep

@Keep
data class ErrorWithRetry<T>(val response: T, val onRetry: () -> Unit)
