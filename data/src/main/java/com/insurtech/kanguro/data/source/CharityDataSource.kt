package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.CharityDto
import com.insurtech.kanguro.networking.dto.DonationDto
import retrofit2.http.Body

interface CharityDataSource {

    suspend fun getAll(): List<CharityDto>

    suspend fun syncUserDonation(@Body donationBody: DonationDto)
}
