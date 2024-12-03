package com.insurtech.kanguro.core.repository.claims

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.networking.dto.ErrorDto
import okhttp3.ResponseBody

interface IClaimsRepository {

    suspend fun getClaims(status: ClaimStatus? = null): NetworkResponse<List<Claim>, ErrorDto>

    suspend fun getClaimDetail(claimId: String): NetworkResponse<Claim, ErrorDto>

    suspend fun getClaimDocument(
        claimId: String,
        documentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto>

    suspend fun getClaimCommunications(claimId: String): NetworkResponse<List<CommunicationDto>, ErrorDto>

    suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBody: CommunicationBody
    ): NetworkResponse<List<CommunicationDto>, ErrorDto>
}
