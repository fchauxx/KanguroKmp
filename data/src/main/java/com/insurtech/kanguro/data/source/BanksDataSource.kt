package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.BankInfoDto

interface BanksDataSource {

    suspend fun getBanks(): List<BankInfoDto>
}
