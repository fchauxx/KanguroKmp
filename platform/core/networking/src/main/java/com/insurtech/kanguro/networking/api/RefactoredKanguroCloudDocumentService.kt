package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.CloudClaimDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentDto
import com.insurtech.kanguro.networking.dto.CloudDocumentPolicyDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RefactoredKanguroCloudDocumentService {

    @GET("api/CloudDocuments")
    suspend fun getCloudDocuments(): CloudDocumentDto

    @GET("/api/CloudDocuments/{policyId}")
    suspend fun getCloudDocumentPolicy(
        @Path("policyId") policyId: String
    ): CloudDocumentPolicyDto

    @GET("/api/CloudDocuments/{policyId}/{claimId}")
    suspend fun getCloudClaimDocument(
        @Path("policyId") policyId: String,
        @Path("claimId") claimId: String
    ): CloudClaimDocumentDto
}
