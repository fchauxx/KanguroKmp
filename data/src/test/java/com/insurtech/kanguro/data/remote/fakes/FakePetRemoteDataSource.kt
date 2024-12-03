package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.domain.model.Pet

class FakePetRemoteDataSource : PetDataSource {

    private var pet: Pet? = null
    private var exception: Exception? = null

    fun setPet(pet: Pet) {
        this.pet = pet
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getPetDetails(petId: Long): Pet = pet ?: throw exception!!

    override suspend fun getUserPets(): List<Pet> {
        TODO("Not yet implemented")
    }
}
