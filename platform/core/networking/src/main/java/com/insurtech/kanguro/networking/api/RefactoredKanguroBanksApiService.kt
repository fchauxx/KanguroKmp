package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.BankInfoDto
import retrofit2.http.GET

interface RefactoredKanguroBanksApiService {

    @GET("api/banks")
    suspend fun getBanks(): List<BankInfoDto>
}
