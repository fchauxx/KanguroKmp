package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.ContactInformationDataSource
import com.insurtech.kanguro.networking.api.KanguroContactInformationApiService
import com.insurtech.kanguro.networking.dto.ContactInformationDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class ContactInformationRemoteDataSource @Inject constructor(
    private val contactInformationService: KanguroContactInformationApiService
) : ContactInformationDataSource {
    override suspend fun getContactInformation(): List<ContactInformationDto> =
        managedExecution {
            contactInformationService.getContactInformation()
        }
}
