package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.ExternalLinksDto

interface ExternalLinksDataSource {
    suspend fun getExternalLinks(advertiserId: String, userId: String): ExternalLinksDto
}
