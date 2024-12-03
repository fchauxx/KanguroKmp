package com.insurtech.kanguro.networking.dto

import com.squareup.moshi.Json

data class NearbySearchDto(
    val results: List<PlaceDto>,
    @Json(name = "next_page_token")
    val nextPageToken: String?
)

data class PlaceDetailSearchDto(
    val result: PlaceDto
)
