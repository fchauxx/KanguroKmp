package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.KeycloakDto
import com.insurtech.kanguro.networking.dto.LoginBodyDto
import com.insurtech.kanguro.networking.dto.LoginDto
import com.insurtech.kanguro.networking.dto.RefreshTokenBodyDto
import com.insurtech.kanguro.networking.dto.ResetPasswordBodyDto

interface LoginDataSource {

    suspend fun sendLogin(
        body: LoginBodyDto
    ): LoginDto

    suspend fun resetPassword(
        body: ResetPasswordBodyDto
    )

    suspend fun refreshToken(
        body: RefreshTokenBodyDto
    ): LoginDto

    suspend fun keycloak(): KeycloakDto
}
