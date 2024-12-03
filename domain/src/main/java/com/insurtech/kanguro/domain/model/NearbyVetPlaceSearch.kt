package com.insurtech.kanguro.domain.model

data class NearbyVetPlaceSearch(
    val vetPlaces: List<VetPlace>,
    val nextPageToken: String?
)
