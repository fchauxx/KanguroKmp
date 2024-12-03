package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.KanguroParameter
import com.insurtech.kanguro.networking.dto.KanguroParameterDto

object KanguroParameterMapper {

    private fun mapKanguroParameterDtoToKanguroParameter(kanguroParameterDto: KanguroParameterDto): KanguroParameter =
        KanguroParameter(
            key = kanguroParameterDto.key,
            value = kanguroParameterDto.value,
            description = kanguroParameterDto.description,
            type = kanguroParameterDto.type,
            language = kanguroParameterDto.language,
            isActive = kanguroParameterDto.isActive
        )

    fun mapKanguroParameterDtosToKanguroParameters(kanguroParameterDtos: List<KanguroParameterDto>): List<KanguroParameter> =
        kanguroParameterDtos.map(::mapKanguroParameterDtoToKanguroParameter)
}
