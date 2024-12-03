package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.ContactInformationDto

interface ContactInformationDataSource {
    suspend fun getContactInformation(): List<ContactInformationDto>
}
