package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Deductible
import com.insurtech.kanguro.networking.dto.DeductibleDto

object DeductibleMapper {

    fun mapDeductibleDtoToDeductible(deductibleDto: DeductibleDto): Deductible =
        Deductible(
            id = deductibleDto.id,
            limit = deductibleDto.limit,
            consumed = deductibleDto.consumed
        )
}
