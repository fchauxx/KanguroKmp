package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.ContactInformation

interface IContactInformationRepository {

    suspend fun getContactInformation(): Result<List<ContactInformation>>
}
