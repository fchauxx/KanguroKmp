package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.ContactInformationMapper
import com.insurtech.kanguro.data.repository.IContactInformationRepository
import com.insurtech.kanguro.data.source.ContactInformationDataSource
import com.insurtech.kanguro.domain.model.ContactInformation
import javax.inject.Inject

class ContactInformationRepository @Inject constructor(
    private val contactInformationDataSource: ContactInformationDataSource
) : IContactInformationRepository {
    override suspend fun getContactInformation(): Result<List<ContactInformation>> {
        try {
            val contactInformation = contactInformationDataSource.getContactInformation()
            return Result.Success(
                ContactInformationMapper.mapContactsInformationDtoToContactsInformation(
                    contactInformation
                )
            )
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}
