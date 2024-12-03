package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.AmountInfo
import com.insurtech.kanguro.networking.dto.AmountInfoDto

object AmountInfoMapper {

    fun mapAmountInfoDtoToAmountInfo(amountInfoDto: AmountInfoDto): AmountInfo =
        AmountInfo(
            id = amountInfoDto.id,
            limit = amountInfoDto.limit,
            consumed = amountInfoDto.consumed,
            remainingValue = amountInfoDto.remainingValue
        )
}
