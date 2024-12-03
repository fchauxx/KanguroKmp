package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.networking.dto.AppLanguageDto

object AppLanguageMapper {

    fun mapAppLanguageDtoToLanguage(dto: AppLanguageDto): AppLanguage {
        return when (dto) {
            AppLanguageDto.English -> AppLanguage.English
            AppLanguageDto.Spanish -> AppLanguage.Spanish
        }
    }

    fun mapAppLanguageToAppLanguageDto(language: AppLanguage): AppLanguageDto {
        return when (language) {
            AppLanguage.English -> AppLanguageDto.English
            AppLanguage.Spanish -> AppLanguageDto.Spanish
        }
    }
}
