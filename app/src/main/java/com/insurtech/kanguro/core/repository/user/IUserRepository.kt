package com.insurtech.kanguro.core.repository.user

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.api.bodies.TokenBody
import com.insurtech.kanguro.core.api.responses.UserResponse
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.login.KeycloakResponse
import com.insurtech.kanguro.networking.dto.ErrorDto

interface IUserRepository {
    suspend fun resetPassword(email: String): CompletableResponse<ErrorDto>
    suspend fun getReminders(): NetworkResponse<List<Reminder>, ErrorDto>
    suspend fun setPreferredLanguage(language: AppLanguage): CompletableResponse<ErrorDto>

    suspend fun keycloak(): NetworkResponse<KeycloakResponse, ErrorDto>

    suspend fun putUserFirebaseToken(token: TokenBody): CompletableResponse<ErrorDto>

    suspend fun getHasAccessBlocked(userId: String): NetworkResponse<Boolean, ErrorDto>

    suspend fun getUser(): NetworkResponse<UserResponse, ErrorDto>
}
