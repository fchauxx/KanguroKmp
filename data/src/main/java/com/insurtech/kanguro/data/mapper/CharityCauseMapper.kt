package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.CharityCause
import com.insurtech.kanguro.networking.dto.CharityCauseDto

object CharityCauseMapper {

    fun mapCharityCauseDtpToCharityCause(dto: CharityCauseDto): CharityCause {
        return when (dto) {
            CharityCauseDto.Animals -> CharityCause.Animals
            CharityCauseDto.GlobalWarming -> CharityCause.GlobalWarming
            CharityCauseDto.SocialCauses -> CharityCause.SocialCauses
            CharityCauseDto.LatinCommunities -> CharityCause.LatinCommunities
        }
    }

    fun mapCharityCauseToCharityCauseDto(cause: CharityCause): CharityCauseDto {
        return when (cause) {
            CharityCause.Animals -> CharityCauseDto.Animals
            CharityCause.GlobalWarming -> CharityCauseDto.GlobalWarming
            CharityCause.SocialCauses -> CharityCauseDto.SocialCauses
            CharityCause.LatinCommunities -> CharityCauseDto.LatinCommunities
        }
    }
}
