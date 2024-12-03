package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.Pet

interface PetDataSource {

    suspend fun getPetDetails(petId: Long): Pet

    suspend fun getUserPets(): List<Pet>
}
