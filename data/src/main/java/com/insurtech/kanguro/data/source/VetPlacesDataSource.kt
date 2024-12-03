package com.insurtech.kanguro.data.source

import android.location.Location
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace

interface VetPlacesDataSource {

    suspend fun getNearbyVetPlaces(
        userLocation: Location,
        key: String
    ): NearbyVetPlaceSearch

    suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextToken: String,
        key: String
    ): NearbyVetPlaceSearch

    suspend fun getPlace(
        userLocation: Location,
        placeId: String,
        key: String
    ): VetPlace
}
