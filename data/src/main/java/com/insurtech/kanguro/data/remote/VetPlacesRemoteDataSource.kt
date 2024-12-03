package com.insurtech.kanguro.data.remote

import android.location.Location
import com.insurtech.kanguro.data.mapper.NearbyVetPlaceSearchMapper
import com.insurtech.kanguro.data.mapper.VetPlaceMapper
import com.insurtech.kanguro.data.source.VetPlacesDataSource
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace
import com.insurtech.kanguro.networking.api.GooglePlacesApiService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class VetPlacesRemoteDataSource @Inject constructor(
    private val placesApiService: GooglePlacesApiService
) : VetPlacesDataSource {

    override suspend fun getNearbyVetPlaces(
        userLocation: Location,
        key: String
    ): NearbyVetPlaceSearch = managedExecution {
        NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
            placesApiService.getNearbyVetPlaces(userLocation.toLocationString(), key = key),
            userLocation
        )
    }

    override suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextToken: String,
        key: String
    ): NearbyVetPlaceSearch = managedExecution {
        NearbyVetPlaceSearchMapper.mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
            placesApiService.getAdditionalPlaces(nextToken, key = key),
            userLocation
        )
    }

    override suspend fun getPlace(userLocation: Location, placeId: String, key: String): VetPlace =
        managedExecution {
            VetPlaceMapper.mapFromPlaceResponse(
                placesApiService.getPlace(placeId, key = key).result,
                userLocation
            )
        }

    private fun Location.toLocationString() = "${this.latitude},${this.longitude}"
}
