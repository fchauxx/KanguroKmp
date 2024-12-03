package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.PreventiveCoverageInfoDto

object PreventiveCoverageInfoMapper {

    fun mapPreventiveCoverageInfoDtosToPreventiveCoverageInfos(
        preventiveCoverageInfoDtos: List<PreventiveCoverageInfoDto>
    ): List<PreventiveCoverageInfo> {
        return preventiveCoverageInfoDtos.map(::mapPreventiveCoverageInfoDtoToPreventiveCoverageInfo)
    }

    private fun mapPreventiveCoverageInfoDtoToPreventiveCoverageInfo(
        preventiveCoverageInfoDto: PreventiveCoverageInfoDto
    ): PreventiveCoverageInfo {
        return PreventiveCoverageInfo(
            name = preventiveCoverageInfoDto.name,
            value = preventiveCoverageInfoDto.value,
            usedValue = preventiveCoverageInfoDto.usedValue,
            remainingValue = preventiveCoverageInfoDto.remainingValue,
            annualLimit = preventiveCoverageInfoDto.annualLimit,
            remainingLimit = preventiveCoverageInfoDto.remainingLimit,
            uses = preventiveCoverageInfoDto.uses,
            remainingUses = preventiveCoverageInfoDto.remainingUses,
            varName = preventiveCoverageInfoDto.varName,
            usesLimit = preventiveCoverageInfoDto.usesLimit
        )
    }
}
