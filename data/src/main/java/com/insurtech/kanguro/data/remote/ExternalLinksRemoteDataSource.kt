package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.ExternalLinksDataSource
import com.insurtech.kanguro.networking.api.KanguroExternalLinksApiService
import com.insurtech.kanguro.networking.dto.ExternalLinksDto
import javax.inject.Inject

class ExternalLinksRemoteDataSource @Inject constructor(
    private val kanguroExternalLinksApiService: KanguroExternalLinksApiService
) : ExternalLinksDataSource {
    override suspend fun getExternalLinks(advertiserId: String, userId: String): ExternalLinksDto =
        kanguroExternalLinksApiService.getExternalLinks(advertiserId, userId)
}
