package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.BankInfoMapper
import com.insurtech.kanguro.data.repository.IRefactoredBanksRepository
import com.insurtech.kanguro.data.source.BanksDataSource
import com.insurtech.kanguro.domain.model.BankInfo
import javax.inject.Inject

class RefactoredBanksRepository @Inject constructor(
    private val banksDataSource: BanksDataSource
) : IRefactoredBanksRepository {

    override suspend fun getBanks(): Result<List<BankInfo>> {
        return try {
            val bankInfo = BankInfoMapper.mapBanksInfoDtoToBanksInfo(
                banksDataSource.getBanks()
            )

            Result.Success(bankInfo)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
