package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredPetRepository
import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.domain.model.Pet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefactoredPetRepository @Inject constructor(
    private val petRemoteDataSource: PetDataSource
) : IRefactoredPetRepository {

    override suspend fun getPetDetail(petId: Long): Flow<Result<Pet>> = flow {
        try {
            val pet = petRemoteDataSource.getPetDetails(petId)
            emit(Result.Success(pet))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getUserPets(): Result<List<Pet>> {
        return try {
            val pets = petRemoteDataSource.getUserPets()
            Result.Success(pets)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
