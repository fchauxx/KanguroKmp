package com.insurtech.kanguro.domain.login

import android.os.Parcelable
import com.auth0.android.jwt.JWT
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeycloakResponse(
    @Json(name = "access_token")
    val accessToken: JWT?,
    @Json(name = "refresh_token")
    val refreshToken: JWT?
) : Parcelable
