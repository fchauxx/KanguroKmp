package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.VeterinarianMapper
import com.insurtech.kanguro.data.repository.IVeterinarianRepository
import com.insurtech.kanguro.data.source.VeterinarianDataSource
import com.insurtech.kanguro.domain.model.Veterinarian
import javax.inject.Inject

class VeterinarianRepository @Inject constructor(
    private val veterinarianDataSource: VeterinarianDataSource
) : IVeterinarianRepository {

    override suspend fun getVeterinarians(): Result<List<Veterinarian>> {
        return try {
            val veterinarians = VeterinarianMapper.mapVeterinariansDtoToVeterinarians(
                veterinarianDataSource.getVeterinarians()
            )
            if (veterinarians.isNotEmpty()) {
                Result.Success(veterinarians)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
