package com.insurtech.kanguro.core.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KanguroPolicyApiService {

    @GET("api/policy")
    suspend fun getPolicies(): NetworkResponse<List<PetPolicyViewModelDto>, ErrorDto>

    @GET("api/policy/{id}")
    suspend fun getPolicyDetail(
        @Path("id") policyId: String
    ): NetworkResponse<PetPolicyViewModelDto, ErrorDto>

    @GET("api/policy/{policy_id}/documents")
    suspend fun getPolicyDocuments(@Path("policy_id") policyId: String): NetworkResponse<List<PolicyDocument>, ErrorDto>

    @GET("api/policy/{policy_id}/documents/{document_id}")
    suspend fun getPolicyDocumentContent(
        @Path("policy_id") policyId: String,
        @Path("document_id") documentId: Long,
        @Query("filename") filename: String
    ): NetworkResponse<ResponseBody, ErrorDto>

    @GET("api/renters/policy/{policy_id}/documents/{document_id}")
    suspend fun getPolicyRentersDocumentContent(
        @Path("policy_id") policyId: String,
        @Path("document_id") documentId: Long,
        @Query("filename") filename: String
    ): NetworkResponse<ResponseBody, ErrorDto>

    @GET("api/policy/{policy_id}/coverage")
    suspend fun getPolicyCoverage(
        @Path("policy_id") policyId: String,
        @Query("reimbursement") reimbursement: Double,
        @Query("offerId") offerId: Int?
    ): NetworkResponse<List<PreventiveCoverageInfo>, ErrorDto>

    @GET("api/policy/{policy_id}/attachments/{attachment_id}")
    suspend fun getPolicyAttachmentContent(
        @Path("policy_id") policyId: String,
        @Path("attachment_id") attachmentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto>
}
