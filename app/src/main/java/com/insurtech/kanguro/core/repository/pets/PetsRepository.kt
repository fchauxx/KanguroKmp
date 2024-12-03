package com.insurtech.kanguro.core.repository.pets

import com.haroldadmin.cnradapter.CompletableResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.KanguroPetsApiService
import com.insurtech.kanguro.core.api.bodies.PetPictureBody
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.networking.dto.ErrorDto
import javax.inject.Inject

class PetsRepository @Inject constructor(
    private val petApiService: KanguroPetsApiService
) : IPetsRepository, BaseRepository() {

    override suspend fun getUserPets(): NetworkResponse<List<Pet>, ErrorDto> {
        return getSafeNetworkResponse {
            petApiService.getUserPets()
        }
    }

    override suspend fun getPetDetails(petId: Long): NetworkResponse<Pet, ErrorDto> {
        return getSafeNetworkResponse {
            petApiService.getPetDetail(petId)
        }
    }

    override suspend fun updatePetPicture(petPictureBody: PetPictureBody): CompletableResponse<ErrorDto> {
        return getSafeNetworkResponse {
            petApiService.updatePetPicture(petPictureBody)
        }
    }
}
