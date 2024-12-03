package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.BanksDataSource
import com.insurtech.kanguro.networking.api.RefactoredKanguroBanksApiService
import com.insurtech.kanguro.networking.dto.BankInfoDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class BanksRemoteDataSource @Inject constructor(
    private val banksApiService: RefactoredKanguroBanksApiService
) : BanksDataSource {

    override suspend fun getBanks(): List<BankInfoDto> = managedExecution {
        banksApiService.getBanks()
    }
}
