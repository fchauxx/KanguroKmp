package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.BackendVersion
import com.insurtech.kanguro.networking.dto.BackendVersionDto

object BackendVersionMapper {

    fun mapBackendVersionDtoToBackendVersion(
        backendVersionDto: BackendVersionDto
    ): BackendVersion =
        BackendVersion(
            version = backendVersionDto.version
        )
}
