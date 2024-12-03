package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimStatusDescription
import com.insurtech.kanguro.domain.model.StatusDescriptionType
import com.insurtech.kanguro.networking.dto.ClaimStatusDescriptionDto

object ClaimStatusDescriptionMapper {

    fun mapStatusDescriptionDtoToStatusDescription(dto: ClaimStatusDescriptionDto): ClaimStatusDescription {
        return ClaimStatusDescription(
            type = StatusDescriptionType.fromString(dto.type.orEmpty()),
            description = dto.description.orEmpty()
        )
    }
}
