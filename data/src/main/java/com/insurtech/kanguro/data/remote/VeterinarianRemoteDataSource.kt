package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.VeterinarianDataSource
import com.insurtech.kanguro.networking.api.KanguroVeterinarianApiService
import com.insurtech.kanguro.networking.dto.VeterinarianDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class VeterinarianRemoteDataSource @Inject constructor(
    private val veterinarianApiService: KanguroVeterinarianApiService
) : VeterinarianDataSource {

    override suspend fun getVeterinarians(): List<VeterinarianDto> = managedExecution {
        veterinarianApiService.getVeterinarians()
    }
}
