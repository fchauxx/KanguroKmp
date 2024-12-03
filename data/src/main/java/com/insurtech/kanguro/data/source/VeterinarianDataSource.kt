package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.VeterinarianDto

interface VeterinarianDataSource {

    suspend fun getVeterinarians(): List<VeterinarianDto>
}
