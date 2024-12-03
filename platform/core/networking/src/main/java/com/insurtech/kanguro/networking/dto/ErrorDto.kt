package com.insurtech.kanguro.networking.dto

import androidx.annotation.Keep

@Keep
data class ErrorDto(val statusCode: Int?, val error: String?)
