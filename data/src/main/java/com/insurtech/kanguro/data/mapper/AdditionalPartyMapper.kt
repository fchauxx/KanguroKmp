package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.data.mapper.AddressModelMapper.mapAddressModelDtoToAddressModel
import com.insurtech.kanguro.domain.model.AdditionalParty
import com.insurtech.kanguro.domain.model.AdditionalPartyType
import com.insurtech.kanguro.networking.dto.AdditionalPartyDto
import com.insurtech.kanguro.networking.dto.AdditionalPartyTypeDto

object AdditionalPartyMapper {

    private fun mapAdditionalPartyDtoToAdditionalParty(additionalPartyDto: AdditionalPartyDto): AdditionalParty? {
        return AdditionalParty(
            id = additionalPartyDto.id ?: return null,
            type = when (additionalPartyDto.type) {
                AdditionalPartyTypeDto.Spouse -> AdditionalPartyType.Spouse
                AdditionalPartyTypeDto.Child -> AdditionalPartyType.Child
                AdditionalPartyTypeDto.Roommate -> AdditionalPartyType.Roommate
                AdditionalPartyTypeDto.Landlord -> AdditionalPartyType.Landlord
                AdditionalPartyTypeDto.PropertyManager -> AdditionalPartyType.PropertyManager
                null -> return null
            },
            fullName = additionalPartyDto.fullName ?: return null,
            email = additionalPartyDto.email ?: return null,
            birthDate = additionalPartyDto.birthDate,
            address = mapAddressModelDtoToAddressModel(additionalPartyDto.address)
        )
    }

    fun mapAdditionalPartiesDtoToAdditionalParties(additionalPartiesDto: List<AdditionalPartyDto>): List<AdditionalParty> {
        return additionalPartiesDto.mapNotNull { mapAdditionalPartyDtoToAdditionalParty(it) }
    }
}
