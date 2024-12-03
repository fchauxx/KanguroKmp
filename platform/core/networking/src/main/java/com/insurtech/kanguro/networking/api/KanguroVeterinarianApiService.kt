package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.VeterinarianDto
import retrofit2.http.GET

interface KanguroVeterinarianApiService {

    @GET("/api/Veterinarians")
    suspend fun getVeterinarians(): List<VeterinarianDto>
}
