package com.insurtech.kanguro.networking.dto

data class ContactInformationDto(
    val type: ContactInformationTypeDto,
    val action: String?,
    val data: ContactInformationDataDto
)
