package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ContactInformationDto
import retrofit2.http.GET

interface KanguroContactInformationApiService {
    @GET("api/ContactInformation")
    suspend fun getContactInformation(): List<ContactInformationDto>
}
