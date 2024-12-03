package com.insurtech.kanguro.core.repository.policy

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import okhttp3.ResponseBody

interface IPolicyRepository {
    suspend fun getPolicies(): NetworkResponse<List<PetPolicy>, ErrorDto>

    suspend fun getPolicyDetail(policyId: String): NetworkResponse<PetPolicyViewModelDto, ErrorDto>

    suspend fun getPolicyDocuments(policyId: String): NetworkResponse<List<PolicyDocument>, ErrorDto>

    suspend fun getPolicyDocumentContent(
        policyId: String,
        documentId: Long,
        fileName: String
    ): NetworkResponse<ResponseBody, ErrorDto>

    suspend fun getPolicyRentersDocumentContent(
        policyId: String,
        documentId: Long,
        fileName: String
    ): NetworkResponse<ResponseBody, ErrorDto>

    suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int? = null
    ): NetworkResponse<List<PreventiveCoverageInfo>, ErrorDto>

    suspend fun getPolicyAttachmentContent(
        policyId: String,
        attachmentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto>
}
