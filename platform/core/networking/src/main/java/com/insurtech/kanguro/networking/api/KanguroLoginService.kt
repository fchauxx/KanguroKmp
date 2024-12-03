package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.KeycloakDto
import com.insurtech.kanguro.networking.dto.LoginBodyDto
import com.insurtech.kanguro.networking.dto.LoginDto
import com.insurtech.kanguro.networking.dto.RefreshTokenBodyDto
import com.insurtech.kanguro.networking.dto.ResetPasswordBodyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KanguroLoginService {
    @POST("api/user/login")
    @UnauthorizedRequest
    suspend fun sendLogin(
        @Body body: LoginBodyDto
    ): LoginDto

    @POST("api/user/resetPassword/sendEmail")
    @UnauthorizedRequest
    suspend fun resetPassword(
        @Body body: ResetPasswordBodyDto
    ): Response<Unit>

    @POST("api/user/refreshToken")
    @UnauthorizedRequest
    suspend fun refreshToken(
        @Body body: RefreshTokenBodyDto
    ): LoginDto

    @GET("api/keycloack")
    suspend fun keycloak(): KeycloakDto
}
