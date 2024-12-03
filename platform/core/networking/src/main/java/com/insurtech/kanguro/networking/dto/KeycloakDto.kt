package com.insurtech.kanguro.networking.dto

import com.auth0.android.jwt.JWT
import com.squareup.moshi.Json

data class KeycloakDto(
    @Json(name = "access_token")
    val accessToken: JWT?,
    @Json(name = "refresh_token")
    val refreshToken: JWT?
)
