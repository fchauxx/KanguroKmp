package com.insurtech.kanguro.data.mapper

import android.location.Location
import com.insurtech.kanguro.domain.model.NearbyVetPlaceSearch
import com.insurtech.kanguro.networking.dto.NearbySearchDto

object NearbyVetPlaceSearchMapper {

    fun mapNearBySearchDtoToNearbyVetPlaceSearchMapper(
        nearbySearchDto: NearbySearchDto,
        userLocation: Location
    ): NearbyVetPlaceSearch {
        return NearbyVetPlaceSearch(
            vetPlaces = nearbySearchDto.results.map {
                VetPlaceMapper.mapFromPlaceResponse(it, userLocation)
            },
            nextPageToken = nearbySearchDto.nextPageToken
        )
    }
}
