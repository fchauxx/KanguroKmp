package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.CharityCause
import com.insurtech.kanguro.domain.model.Donation
import com.insurtech.kanguro.networking.dto.DonationDto

object DonationMapper {

    fun mapDonationDtoToDonation(dto: DonationDto?): Donation? {
        return Donation(
            userId = dto?.userId ?: return null,
            charityId = dto.charityId ?: return null,
            title = dto.title ?: return null,
            cause = CharityCauseMapper.mapCharityCauseDtpToCharityCause(dto.cause ?: return null)
        )
    }

    fun mapDonationToDonationDto(donation: Donation?): DonationDto {
        return DonationDto(
            userId = donation?.userId,
            charityId = donation?.charityId,
            title = donation?.title,
            cause = CharityCauseMapper.mapCharityCauseToCharityCauseDto(
                donation?.cause ?: CharityCause.Animals
            )
        )
    }
}
