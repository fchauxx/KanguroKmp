package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.LoginDataSource
import com.insurtech.kanguro.networking.api.KanguroLoginService
import com.insurtech.kanguro.networking.dto.KeycloakDto
import com.insurtech.kanguro.networking.dto.LoginBodyDto
import com.insurtech.kanguro.networking.dto.LoginDto
import com.insurtech.kanguro.networking.dto.RefreshTokenBodyDto
import com.insurtech.kanguro.networking.dto.ResetPasswordBodyDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val kanguroLoginService: KanguroLoginService
) : LoginDataSource {

    override suspend fun sendLogin(body: LoginBodyDto): LoginDto = managedExecution {
        kanguroLoginService.sendLogin(body)
    }

    override suspend fun resetPassword(body: ResetPasswordBodyDto): Unit = managedExecution {
        kanguroLoginService.resetPassword(body)
    }

    override suspend fun refreshToken(body: RefreshTokenBodyDto): LoginDto = managedExecution {
        kanguroLoginService.refreshToken(body)
    }

    override suspend fun keycloak(): KeycloakDto = managedExecution {
        kanguroLoginService.keycloak()
    }
}
