package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.NearbySearchDto
import com.insurtech.kanguro.networking.dto.PlaceDetailSearchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {
    @GET("api/place/nearbysearch/json")
    suspend fun getNearbyVetPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int = SEARCH_RADIUS,
        @Query("type") type: String = "veterinary_care",
        @Query("key") key: String
    ): NearbySearchDto

    @GET("api/place/nearbysearch/json")
    suspend fun getAdditionalPlaces(
        @Query("pagetoken") nextToken: String,
        @Query("key") key: String
    ): NearbySearchDto

    @GET("api/place/details/json")
    suspend fun getPlace(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = "place_id,name,formatted_address,geometry,formatted_phone_number,opening_hours",
        @Query("key") key: String
    ): PlaceDetailSearchDto

    companion object {
        const val SEARCH_RADIUS = 1_000
    }
}
