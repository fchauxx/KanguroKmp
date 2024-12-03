package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.BackendVersionMapper
import com.insurtech.kanguro.data.source.BackendVersionDataSource
import com.insurtech.kanguro.domain.model.BackendVersion
import com.insurtech.kanguro.networking.api.KanguroApiVersionService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class BackendVersionRemoteDataSource @Inject constructor(
    private val kanguroBackendVersionApiService: KanguroApiVersionService
) : BackendVersionDataSource {

    override suspend fun getBackendVersion(): BackendVersion =
        managedExecution {
            BackendVersionMapper
                .mapBackendVersionDtoToBackendVersion(
                    kanguroBackendVersionApiService.getBackendVersion()
                )
        }
}
