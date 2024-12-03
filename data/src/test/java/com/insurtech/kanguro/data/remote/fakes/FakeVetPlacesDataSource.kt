package com.insurtech.kanguro.data.remote.fakes

import android.location.Location
import com.insurtech.kanguro.data.source.VetPlacesDataSource
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.domain.model.VetPlace

class FakeVetPlacesDataSource : VetPlacesDataSource {

    private var nearbyVetPlaceSearch: NearbyVetPlaceSearch? = null
    private var vetPlace: VetPlace? = null
    private var exception: Exception? = null

    fun setNearbyVetPlaceSearch(nearbyVetPlaceSearch: NearbyVetPlaceSearch) {
        this.nearbyVetPlaceSearch = nearbyVetPlaceSearch
    }

    fun setVetPlace(vetPlace: VetPlace) {
        this.vetPlace = vetPlace
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getNearbyVetPlaces(
        userLocation: Location,
        key: String
    ): NearbyVetPlaceSearch = nearbyVetPlaceSearch ?: throw exception!!

    override suspend fun getAdditionalPlaces(
        userLocation: Location,
        nextToken: String,
        key: String
    ): NearbyVetPlaceSearch = nearbyVetPlaceSearch ?: throw exception!!

    override suspend fun getPlace(userLocation: Location, placeId: String, key: String): VetPlace =
        vetPlace ?: throw exception!!
}
