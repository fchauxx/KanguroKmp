package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.KeycloakMapper
import com.insurtech.kanguro.data.mapper.LoginMapper
import com.insurtech.kanguro.data.repository.ILoginRepository
import com.insurtech.kanguro.data.source.LoginDataSource
import com.insurtech.kanguro.domain.model.Keycloak
import com.insurtech.kanguro.domain.model.Login
import com.insurtech.kanguro.domain.model.LoginBody
import com.insurtech.kanguro.domain.model.ResetPasswordBody
import com.insurtech.kanguro.networking.dto.RefreshTokenBodyDto
import com.insurtech.kanguro.networking.dto.ResetPasswordBodyDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginDataSource: LoginDataSource
) : ILoginRepository {

    override suspend fun sendLogin(body: LoginBody): Flow<Result<Login>> = flow {
        try {
            val loginDto = loginDataSource.sendLogin(LoginMapper.mapLoginBodyToLoginBodyDto(body))
            val login = LoginMapper.mapLoginDtoToLogin(loginDto)
            emit(Result.Success(login))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun resetPassword(body: ResetPasswordBody): Result<Unit> {
        return try {
            loginDataSource.resetPassword(ResetPasswordBodyDto(body.email))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<Login> {
        return try {
            val loginDto = loginDataSource.refreshToken(RefreshTokenBodyDto(refreshToken))
            val login = LoginMapper.mapLoginDtoToLogin(loginDto)
            Result.Success(login)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun keycloak(): Flow<Result<Keycloak>> = flow {
        try {
            val keycloakDto = loginDataSource.keycloak()
            val keycloak = KeycloakMapper.mapKeycloakDtoToKeycloak(keycloakDto)
            emit(Result.Success(keycloak))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
