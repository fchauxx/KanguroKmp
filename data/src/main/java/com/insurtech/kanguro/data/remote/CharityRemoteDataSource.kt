package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.CharityDataSource
import com.insurtech.kanguro.networking.api.KanguroCharityApiService
import com.insurtech.kanguro.networking.dto.CharityDto
import com.insurtech.kanguro.networking.dto.DonationDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class CharityRemoteDataSource @Inject constructor(
    private val charityApiService: KanguroCharityApiService
) : CharityDataSource {

    override suspend fun getAll(): List<CharityDto> = managedExecution {
        charityApiService.getAll()
    }

    override suspend fun syncUserDonation(donationBody: DonationDto): Unit = managedExecution {
        charityApiService.syncUserDonation(donationBody)
    }
}
