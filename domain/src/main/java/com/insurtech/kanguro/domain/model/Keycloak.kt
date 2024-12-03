package com.insurtech.kanguro.domain.model

import com.auth0.android.jwt.JWT

data class Keycloak(
    val accessToken: JWT?,
    val refreshToken: JWT?
)
