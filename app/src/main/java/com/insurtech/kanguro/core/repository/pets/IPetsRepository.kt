package com.insurtech.kanguro.core.repository.pets

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.bodies.PetPictureBody
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.dto.ErrorDto

interface IPetsRepository {
    suspend fun getUserPets(): NetworkResponse<List<Pet>, ErrorDto>

    suspend fun getPetDetails(petId: Long): NetworkResponse<Pet, ErrorDto>

    suspend fun updatePetPicture(petPictureBody: PetPictureBody): CompletableResponse<ErrorDto>
}
