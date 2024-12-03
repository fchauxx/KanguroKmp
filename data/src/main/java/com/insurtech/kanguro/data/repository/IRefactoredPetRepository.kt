package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface IRefactoredPetRepository {

    suspend fun getPetDetail(petId: Long): Flow<Result<Pet>>

    suspend fun getUserPets(): Result<List<Pet>>
}
