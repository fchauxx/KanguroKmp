package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.Login
import com.insurtech.kanguro.domain.model.LoginBody
import com.insurtech.kanguro.networking.dto.AppLanguageDto
import com.insurtech.kanguro.networking.dto.LoginBodyDto
import com.insurtech.kanguro.networking.dto.LoginDto

object LoginMapper {

    fun mapLoginDtoToLogin(dto: LoginDto): Login {
        return Login(
            id = dto.id,
            givenName = dto.givenName,
            surname = dto.surname,
            accessToken = dto.accessToken,
            language = AppLanguageMapper.mapAppLanguageDtoToLanguage(
                dto.language ?: AppLanguageDto.English
            ),
            referralCode = dto.referralCode,
            email = dto.email,
            phone = dto.phone,
            refreshToken = dto.refreshToken,
            idToken = dto.idToken,
            isNeededDeleteData = dto.isNeededDeleteData,
            isPasswordUpdateNeeded = dto.isPasswordUpdateNeeded,
            donation = DonationMapper.mapDonationDtoToDonation(dto.donation),
            expiresOn = dto.expiresOn
        )
    }

    fun mapLoginBodyToLoginBodyDto(loginBody: LoginBody): LoginBodyDto {
        return LoginBodyDto(
            email = loginBody.email,
            password = loginBody.password
        )
    }
}
