package com.insurtech.kanguro.core.api

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.bodies.*
import com.insurtech.kanguro.core.api.responses.UserResponse
import com.insurtech.kanguro.domain.dashboard.ReminderResponse
import com.insurtech.kanguro.domain.login.KeycloakResponse
import com.insurtech.kanguro.domain.login.LoginResponse
import com.insurtech.kanguro.networking.api.UnauthorizedRequest
import com.insurtech.kanguro.networking.dto.ErrorDto
import retrofit2.http.*

interface KanguroUserApiService {

    @POST("api/user/refreshToken")
    @UnauthorizedRequest
    suspend fun refreshToken(
        @Body body: RefreshTokenBody
    ): NetworkResponse<LoginResponse, ErrorDto>

    @POST("api/user/resetPassword/sendEmail")
    @UnauthorizedRequest
    suspend fun resetPassword(
        @Body body: ResetPasswordBody
    ): CompletableResponse<ErrorDto>

    @GET("api/user/reminders")
    suspend fun getReminders(): NetworkResponse<List<ReminderResponse>, ErrorDto>

    @PUT("api/user/preferredLanguage")
    suspend fun putUserPreferredLanguage(
        @Body body: UserUpdateLanguagePreferenceBody
    ): CompletableResponse<ErrorDto>

    @GET("api/keycloack")
    suspend fun keycloak(): NetworkResponse<KeycloakResponse, ErrorDto>

    @PUT("api/user/UserFirebaseToken")
    suspend fun putUserFirebaseToken(@Body token: TokenBody): CompletableResponse<ErrorDto>

    @GET("/api/user/{userId}/HasAccessBlocked")
    suspend fun getHasAccessBlocked(@Path("userId") userId: String): NetworkResponse<Boolean, ErrorDto>

    @GET("/api/user")
    suspend fun getUser(): NetworkResponse<UserResponse, ErrorDto>
}
