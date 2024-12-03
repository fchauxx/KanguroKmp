package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.BankInfo
import com.insurtech.kanguro.networking.dto.BankInfoDto

object BankInfoMapper {

    private fun mapBankInfoDtoToBankInfo(bankInfoDto: BankInfoDto): BankInfo? {
        return BankInfo(
            id = bankInfoDto.id ?: return null,
            name = bankInfoDto.name ?: return null
        )
    }

    fun mapBanksInfoDtoToBanksInfo(banksInfoDto: List<BankInfoDto>): List<BankInfo> {
        return banksInfoDto.mapNotNull { mapBankInfoDtoToBankInfo(it) }
    }
}
