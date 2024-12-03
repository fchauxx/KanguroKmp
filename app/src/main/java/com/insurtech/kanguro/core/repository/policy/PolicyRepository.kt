package com.insurtech.kanguro.core.repository.policy

import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.api.KanguroPetsApiService
import com.insurtech.kanguro.core.api.KanguroPolicyApiService
import com.insurtech.kanguro.core.repository.RepoUtils
import com.insurtech.kanguro.data.mapper.PolicyMapper
import com.insurtech.kanguro.data.repository.BaseRepository
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.extensions.convert
import okhttp3.ResponseBody
import javax.inject.Inject

class PolicyRepository @Inject constructor(
    private val service: KanguroPolicyApiService,
    private val petsApiService: KanguroPetsApiService
) : IPolicyRepository, BaseRepository() {

    override suspend fun getPolicies(): NetworkResponse<List<PetPolicy>, ErrorDto> {
        return getSafeNetworkResponse {
            val policyResponse = service.getPolicies()

            val returnedPolicies = (policyResponse as? NetworkResponse.Success)?.body.orEmpty()
            val requiredPetsIds = returnedPolicies.mapNotNull { it.petId }.toSet()

            val petDetails = RepoUtils.getPetDetails(petsApiService, requiredPetsIds)

            val convertedPolicies: List<PetPolicy> = returnedPolicies.mapNotNull { policy ->
                PolicyMapper.mapPolicyDtoToPolicy(
                    policy,
                    petDetails[policy.petId] ?: return@mapNotNull null
                )
            }

            policyResponse.convert { convertedPolicies.sortedByDescending { it.pet?.id } }
        }
    }

    override suspend fun getPolicyDetail(policyId: String): NetworkResponse<PetPolicyViewModelDto, ErrorDto> {
        return getSafeNetworkResponse { service.getPolicyDetail(policyId) }
    }

    override suspend fun getPolicyDocumentContent(
        policyId: String,
        documentId: Long,
        fileName: String
    ): NetworkResponse<ResponseBody, ErrorDto> {
        return getSafeNetworkResponse {
            service.getPolicyDocumentContent(policyId, documentId, fileName)
        }
    }

    override suspend fun getPolicyRentersDocumentContent(
        policyId: String,
        documentId: Long,
        fileName: String
    ): NetworkResponse<ResponseBody, ErrorDto> {
        return getSafeNetworkResponse {
            service.getPolicyRentersDocumentContent(policyId, documentId, fileName)
        }
    }

    override suspend fun getPolicyDocuments(policyId: String): NetworkResponse<List<PolicyDocument>, ErrorDto> {
        return getSafeNetworkResponse { service.getPolicyDocuments(policyId) }
    }

    override suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ): NetworkResponse<List<PreventiveCoverageInfo>, ErrorDto> {
        return getSafeNetworkResponse {
            service.getPolicyCoverage(
                policyId,
                reimbursement,
                offerId
            )
        }
    }

    override suspend fun getPolicyAttachmentContent(
        policyId: String,
        attachmentId: Long
    ): NetworkResponse<ResponseBody, ErrorDto> {
        return getSafeNetworkResponse { service.getPolicyAttachmentContent(policyId, attachmentId) }
    }
}
