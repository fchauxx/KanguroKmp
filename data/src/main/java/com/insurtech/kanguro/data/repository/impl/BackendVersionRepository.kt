package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IBackendVersionRepository
import com.insurtech.kanguro.data.source.BackendVersionDataSource
import com.insurtech.kanguro.domain.model.BackendVersion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BackendVersionRepository @Inject constructor(
    private val backendVersionRemoteDataSource: BackendVersionDataSource
) : IBackendVersionRepository {

    override suspend fun getBackendVersion(): Flow<Result<BackendVersion>> =
        flow {
            try {
                val backendVersion = backendVersionRemoteDataSource.getBackendVersion()
                emit(Result.Success(backendVersion))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}
