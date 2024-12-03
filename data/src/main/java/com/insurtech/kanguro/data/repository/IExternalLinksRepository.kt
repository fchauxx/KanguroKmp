package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.ExternalLinks

interface IExternalLinksRepository {
    suspend fun getExternalLinks(advertiserId: String, userId: String): Result<ExternalLinks>
}
