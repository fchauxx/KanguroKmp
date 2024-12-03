package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Charity
import com.insurtech.kanguro.domain.model.CharityAttributes
import com.insurtech.kanguro.networking.dto.CharityAttributesDto
import com.insurtech.kanguro.networking.dto.CharityDto

object CharityMapper {

    fun mapCharitiesDtoToCharities(charitiesDto: List<CharityDto>): List<Charity> {
        return charitiesDto.map { charityDto ->
            mapCharityDtoToCharity(charityDto)
        }
    }

    private fun mapCharityDtoToCharity(charityDto: CharityDto): Charity {
        return Charity(
            id = charityDto.id,
            attributes = mapCharityAttributesDtoToCharityAttributes(charityDto.attributes)
        )
    }

    private fun mapCharityAttributesDtoToCharityAttributes(charityAttributesDto: CharityAttributesDto?): CharityAttributes {
        return CharityAttributes(
            title = charityAttributesDto?.title,
            abbreviatedTitle = charityAttributesDto?.abbreviatedTitle,
            description = charityAttributesDto?.description,
            locale = charityAttributesDto?.locale,
            canBeChosenByUser = charityAttributesDto?.canBeChosenByUser,
            charityKey = charityAttributesDto?.charityKey,
            cause = charityAttributesDto?.cause
        )
    }
}
