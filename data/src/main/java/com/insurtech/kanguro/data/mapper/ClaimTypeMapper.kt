package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.networking.dto.ClaimTypeDto

object ClaimTypeMapper {

    fun claimTypeDtoToClaimType(dto: ClaimTypeDto): ClaimType =
        when (dto) {
            ClaimTypeDto.Accident -> ClaimType.Accident
            ClaimTypeDto.Illness -> ClaimType.Illness
            ClaimTypeDto.Other -> ClaimType.Other
        }

    fun claimTypeToClaimTypeDto(type: ClaimType): ClaimTypeDto =
        when (type) {
            ClaimType.Accident -> ClaimTypeDto.Accident
            ClaimType.Illness -> ClaimTypeDto.Illness
            ClaimType.Other -> ClaimTypeDto.Other
        }
}
