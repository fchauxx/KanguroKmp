package com.insurtech.kanguro.core.api.bodies

import androidx.annotation.Keep

@Keep
data class ResetPasswordBody(
    val email: String?
)
