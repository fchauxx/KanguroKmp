package com.insurtech.kanguro.data.repository

import android.location.Location
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace
import kotlinx.coroutines.flow.Flow

interface IVetPlacesRepository {

    suspend fun getNearbyVetPlaces(
        userLocation: Location,
        key: String
    ): Flow<Result<NearbyVetPlaceSearch>>

    suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextToken: String,
        key: String
    ): Flow<Result<NearbyVetPlaceSearch>>

    suspend fun getPlace(
        userLocation: Location,
        placeId: String,
        key: String
    ): Flow<Result<VetPlace>>
}
