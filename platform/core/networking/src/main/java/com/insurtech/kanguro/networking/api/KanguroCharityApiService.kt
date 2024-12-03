package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.CharityDto
import com.insurtech.kanguro.networking.dto.DonationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface KanguroCharityApiService {

    @GET("api/Charity/GetAll")
    suspend fun getAll(): List<CharityDto>

    @PATCH("api/Donation/SyncUserDonation")
    suspend fun syncUserDonation(@Body donationBody: DonationDto): Response<Unit>
}
