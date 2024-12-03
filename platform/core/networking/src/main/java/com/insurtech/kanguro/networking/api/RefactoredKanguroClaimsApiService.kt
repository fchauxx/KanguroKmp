package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentDto
import com.insurtech.kanguro.networking.dto.ClaimDirectPaymentResponseDto
import com.insurtech.kanguro.networking.dto.ClaimDocumentDto
import com.insurtech.kanguro.networking.dto.ClaimDto
import com.insurtech.kanguro.networking.dto.ClaimFeedbackBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationBodyDto
import com.insurtech.kanguro.networking.dto.CommunicationDto
import com.insurtech.kanguro.networking.dto.VeterinarianSignatureDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RefactoredKanguroClaimsApiService {

    @GET("api/claims/{claimId}/documents")
    suspend fun getClaimDocuments(@Path("claimId") claimId: String): List<ClaimDocumentDto>

    @POST("/api/claims/{id}/communications")
    suspend fun sendClaimCommunications(
        @Path("id") claimId: String,
        @Body communication: CommunicationBodyDto
    ): List<CommunicationDto>

    @PUT("/api/claims/{id}/feedback")
    suspend fun putClaimFeedback(
        @Path("id") claimId: String,
        @Body feedback: ClaimFeedbackBodyDto
    ): Response<Unit>

    @POST("/api/claims/DirectPayment")
    suspend fun postDirectPayment(@Body claim: ClaimDirectPaymentDto): ClaimDirectPaymentResponseDto

    @POST("/api/claims/{id}/DirectPaymentVeterinarianSignature")
    suspend fun postDirectPaymentVeterinarianSignature(
        @Path("id") claimId: String,
        @Body veterinarianSignatureDto: VeterinarianSignatureDto
    )

    @GET("/api/claims/{id}/DirectPaymentPreSignedFileUrl")
    suspend fun getDirectPaymentPreSignedFileUrl(
        @Path("id") claimId: String
    ): ResponseBody

    @GET("api/v{version}/claims")
    suspend fun getClaims(
        @Path("version") version: Int
    ): List<ClaimDto>

    @GET("api/v{version}/claims/{id}")
    suspend fun getClaim(
        @Path("version") version: Int,
        @Path("id") id: String
    ): ClaimDto
}
