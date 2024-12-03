package com.insurtech.kanguro.data.repository.impl

import android.location.Location
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IVetPlacesRepository
import com.insurtech.kanguro.data.source.VetPlacesDataSource
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VetPlacesRepository @Inject constructor(
    private val vetPlacesDataSource: VetPlacesDataSource
) : IVetPlacesRepository {
    override suspend fun getNearbyVetPlaces(
        userLocation: Location,
        key: String
    ): Flow<Result<NearbyVetPlaceSearch>> = flow {
        try {
            val result = vetPlacesDataSource.getNearbyVetPlaces(userLocation, key)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextToken: String,
        key: String
    ): Flow<Result<NearbyVetPlaceSearch>> = flow {
        try {
            val result = vetPlacesDataSource.getAdditionalPlaces(userLocation, nextToken, key)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getPlace(
        userLocation: Location,
        placeId: String,
        key: String
    ): Flow<Result<VetPlace>> = flow {
        try {
            val result = vetPlacesDataSource.getPlace(userLocation, placeId, key)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
