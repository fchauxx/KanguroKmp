package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.BankInfo

interface IRefactoredBanksRepository {

    suspend fun getBanks(): Result<List<BankInfo>>
}
