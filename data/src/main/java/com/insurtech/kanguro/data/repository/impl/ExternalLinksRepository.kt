package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.ExternalLinksMapper.toExternalLink
import com.insurtech.kanguro.data.repository.IExternalLinksRepository
import com.insurtech.kanguro.data.source.ExternalLinksDataSource
import com.insurtech.kanguro.domain.model.ExternalLinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExternalLinksRepository @Inject constructor(
    private val externalLinksRemoteDataSource: ExternalLinksDataSource
) : IExternalLinksRepository {
    override suspend fun getExternalLinks(advertiserId: String, userId: String): Result<ExternalLinks> =
        withContext(Dispatchers.IO) {
            try {
                val externalLinksDto = externalLinksRemoteDataSource.getExternalLinks(advertiserId, userId)

                Result.Success(externalLinksDto.toExternalLink())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}
