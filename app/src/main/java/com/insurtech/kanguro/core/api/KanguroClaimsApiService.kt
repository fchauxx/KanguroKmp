package com.insurtech.kanguro.core.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.api.bodies.NewClaimAttachmentsBody
import com.insurtech.kanguro.core.api.bodies.NewClaimBody
import com.insurtech.kanguro.core.api.responses.ClaimResponse
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.networking.dto.ErrorDto
import okhttp3.ResponseBody
import retrofit2.http.*

interface KanguroClaimsApiService {

    @GET("api/claims/internal")
    suspend fun getClaims(
        @Query("claimStatus") status: ClaimStatus?
    ): NetworkResponse<List<Claim>, ErrorDto>

    @GET("api/claims/{claimId}")
    suspend fun getClaimDetail(@Path("claimId") claimId: String): NetworkResponse<Claim, ErrorDto>

    @GET("api/claims/{claimId}/documents/{documentId}")
    suspend fun getClaimDocument(
        @Path("claimId") claimId: String,
        @Path("documentId") documentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto>

    @GET("/api/claims/{id}/communications")
    suspend fun getClaimCommunications(@Path("id") claimId: String): NetworkResponse<List<CommunicationDto>, ErrorDto>

    @POST("/api/claims/{id}/communications")
    suspend fun sendClaimCommunications(
        @Path("id") claimId: String,
        @Body communication: CommunicationBody
    ): NetworkResponse<List<CommunicationDto>, ErrorDto>

    @POST("/api/claims")
    suspend fun saveNewClaim(
        @Body claimBody: NewClaimBody
    ): NetworkResponse<ClaimResponse, ErrorDto>

    @POST("/api/claims/uploadDocuments")
    suspend fun saveNewClaimAttachments(
        @Body attachments: NewClaimAttachmentsBody
    ): NetworkResponse<List<Int>, ErrorDto>
}
