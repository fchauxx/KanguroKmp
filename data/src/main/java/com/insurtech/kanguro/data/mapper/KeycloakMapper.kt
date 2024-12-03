package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Keycloak
import com.insurtech.kanguro.networking.dto.KeycloakDto

object KeycloakMapper {

    fun mapKeycloakDtoToKeycloak(dto: KeycloakDto): Keycloak {
        return Keycloak(
            accessToken = dto.accessToken,
            refreshToken = dto.refreshToken
        )
    }
}
