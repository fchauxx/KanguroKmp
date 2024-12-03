package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.BackendVersionDto
import retrofit2.http.GET

interface KanguroApiVersionService {

    @GET("api/Version")
    suspend fun getBackendVersion(): BackendVersionDto
}
