package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.PetDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RefactoredKanguroPetApiService {

    @GET("api/pets/{petId}")
    suspend fun getPetDetail(
        @Path("petId") petId: Long
    ): PetDto

    @GET("api/pets")
    suspend fun getUserPets(): List<PetDto>
}
