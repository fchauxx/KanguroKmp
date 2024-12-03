package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.BackendVersionDataSource
import com.insurtech.kanguro.domain.model.BackendVersion

class FakeBackendVersionRemoteDataSource : BackendVersionDataSource {

    private var backendVersion: BackendVersion? = null
    private var exception: Exception? = null

    fun setBackendVersion(backendVersion: BackendVersion) {
        this.backendVersion = backendVersion
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getBackendVersion(): BackendVersion = backendVersion ?: throw exception!!
}
