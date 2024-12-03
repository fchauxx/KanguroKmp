package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ExternalLinksDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KanguroExternalLinksApiService {
    @GET("api/ExternalLinks/{id}")
    suspend fun getExternalLinks(
        @Path("id") advertiserId: String,
        @Query("userId") userId: String
    ): ExternalLinksDto
}
