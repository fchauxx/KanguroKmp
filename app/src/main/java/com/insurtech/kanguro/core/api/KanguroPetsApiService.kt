package com.insurtech.kanguro.core.api

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.bodies.PetPictureBody
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.dto.ErrorDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KanguroPetsApiService {

    @GET("api/pets/{petId}")
    suspend fun getPetDetail(
        @Path("petId") petId: Long
    ): NetworkResponse<Pet, ErrorDto>

    @GET("api/pets")
    suspend fun getUserPets(): NetworkResponse<List<Pet>, ErrorDto>

    @GET("api/breeds")
    suspend fun getBreeds(): NetworkResponse<ChatInteractionStep, ErrorDto> // TODO

    @PUT("api/pets/picture")
    suspend fun updatePetPicture(@Body petPictureBody: PetPictureBody): CompletableResponse<ErrorDto>
}
