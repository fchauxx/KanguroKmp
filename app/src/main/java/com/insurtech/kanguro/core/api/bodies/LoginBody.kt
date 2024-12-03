package com.insurtech.kanguro.core.api.bodies

import androidx.annotation.Keep

@Keep
data class LoginBody(
    val email: String?,
    val password: String?
)
