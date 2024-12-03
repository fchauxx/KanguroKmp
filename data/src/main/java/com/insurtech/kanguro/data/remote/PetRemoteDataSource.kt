package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.api.RefactoredKanguroPetApiService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class PetRemoteDataSource @Inject constructor(
    private val petApiService: RefactoredKanguroPetApiService
) : PetDataSource {

    override suspend fun getPetDetails(petId: Long): Pet =
        managedExecution {
            PetMapper.mapPetDtoToPet(
                petApiService.getPetDetail(petId)
            )
        }

    override suspend fun getUserPets(): List<Pet> = managedExecution {
        PetMapper.mapPetsDtoToPets(
            petApiService.getUserPets()
        )
    }
}
