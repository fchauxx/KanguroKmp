package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.BackendVersion

interface BackendVersionDataSource {

    suspend fun getBackendVersion(): BackendVersion
}
