package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.CharityMapper
import com.insurtech.kanguro.data.mapper.DonationMapper
import com.insurtech.kanguro.data.repository.ICharityRepository
import com.insurtech.kanguro.data.source.CharityDataSource
import com.insurtech.kanguro.domain.model.Charity
import com.insurtech.kanguro.domain.model.Donation
import javax.inject.Inject

class CharityRepository @Inject constructor(
    private val charityDataSource: CharityDataSource
) : ICharityRepository {

    override suspend fun getAll(): Result<List<Charity>> {
        try {
            val charitiesDto = charityDataSource.getAll()
            val charities = CharityMapper.mapCharitiesDtoToCharities(charitiesDto)
            return Result.Success(charities)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun syncUserDonation(donation: Donation): Result<Unit> {
        try {
            val donationDto = DonationMapper.mapDonationToDonationDto(donation)
            charityDataSource.syncUserDonation(donationDto)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}
