package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Keycloak
import com.insurtech.kanguro.domain.model.Login
import com.insurtech.kanguro.domain.model.LoginBody
import com.insurtech.kanguro.domain.model.ResetPasswordBody
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {

    suspend fun sendLogin(
        body: LoginBody
    ): Flow<Result<Login>>

    suspend fun resetPassword(
        body: ResetPasswordBody
    ): Result<Unit>

    suspend fun refreshToken(
        refreshToken: String
    ): Result<Login>

    suspend fun keycloak(): Flow<Result<Keycloak>>
}
