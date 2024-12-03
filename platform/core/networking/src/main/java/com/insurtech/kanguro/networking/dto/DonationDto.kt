package com.insurtech.kanguro.networking.dto

data class DonationDto(
    val userId: String?,
    val charityId: Int?,
    val title: String?,
    val cause: CharityCauseDto?
)
