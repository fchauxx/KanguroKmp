package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.BackendVersion
import kotlinx.coroutines.flow.Flow

interface IBackendVersionRepository {
    suspend fun getBackendVersion(): Flow<Result<BackendVersion>>
}
