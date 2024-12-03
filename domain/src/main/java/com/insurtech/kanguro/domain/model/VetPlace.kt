package com.insurtech.kanguro.domain.model

import com.insurtech.kanguro.common.enums.PlaceStatus

data class VetPlace(
    val id: String,
    val name: String,
    val address: String,
    val status: PlaceStatus?,
    val operatingHour: String?,
    val lat: Double?,
    val lng: Double?,
    val phone: String?,
    val distanceFromUser: Double?
)
