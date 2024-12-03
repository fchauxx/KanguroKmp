package com.insurtech.kanguro.core.repository.claims

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.api.KanguroClaimsApiService
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.networking.dto.ErrorDto
import okhttp3.ResponseBody
import javax.inject.Inject

class ClaimsRepository @Inject constructor(
    private val claimsService: KanguroClaimsApiService
) : IClaimsRepository, BaseRepository() {

    override suspend fun getClaims(status: ClaimStatus?): NetworkResponse<List<Claim>, ErrorDto> {
        return getSafeNetworkResponse {
            claimsService.getClaims(status)
        }
    }

    override suspend fun getClaimDetail(claimId: String): NetworkResponse<Claim, ErrorDto> {
        return getSafeNetworkResponse {
            claimsService.getClaimDetail(claimId)
        }
    }

    override suspend fun getClaimDocument(
        claimId: String,
        documentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto> {
        return getSafeNetworkResponse {
            claimsService.getClaimDocument(claimId, documentId)
        }
    }

    override suspend fun getClaimCommunications(claimId: String): NetworkResponse<List<CommunicationDto>, ErrorDto> {
        return getSafeNetworkResponse {
            claimsService.getClaimCommunications(claimId)
        }
    }

    override suspend fun sendClaimCommunications(
        claimId: String,
        claimCommunicationBody: CommunicationBody
    ): NetworkResponse<List<CommunicationDto>, ErrorDto> {
        return getSafeNetworkResponse {
            claimsService.sendClaimCommunications(claimId, claimCommunicationBody)
        }
    }
}
