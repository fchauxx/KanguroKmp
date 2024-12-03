package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.PetMapper
import com.insurtech.kanguro.data.mapper.PolicyMapper
import com.insurtech.kanguro.data.mapper.PreventiveCoverageInfoMapper
import com.insurtech.kanguro.data.source.PolicyDataSource
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.api.RefactoredKanguroPetApiService
import com.insurtech.kanguro.networking.api.RefactoredKanguroPolicyApiService
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class PolicyRemoteDataSource @Inject constructor(
    private val policyService: RefactoredKanguroPolicyApiService,
    private val petService: RefactoredKanguroPetApiService
) : PolicyDataSource {

    override suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ): List<PreventiveCoverageInfo> = managedExecution {
        PreventiveCoverageInfoMapper
            .mapPreventiveCoverageInfoDtosToPreventiveCoverageInfos(
                policyService.getPolicyCoverage(
                    policyId,
                    reimbursement,
                    offerId
                )
            )
    }

    override suspend fun getPolicyDetail(policyId: String): PetPolicy {
        return managedExecution {
            val policyDto = policyService.getPolicyDetail(policyId)

            if (policyDto.petId == null) {
                throw Exception("Pet id in policy is null")
            }

            val petDto = petService.getPetDetail(policyDto.petId!!)

            PolicyMapper.mapPolicyDtoToPolicy(
                policyDto,
                PetMapper.mapPetDtoToPet(petDto)
            )
        }
    }

    override suspend fun getPolicies(): List<PetPolicy> = managedExecution {
        val policies = policyService.getPolicies()

        policies.map { policyDto ->
            if (policyDto.petId == null) {
                throw Exception("Pet id in policy is null")
            }

            val petDto = petService.getPetDetail(policyDto.petId!!)

            PolicyMapper.mapPolicyDtoToPolicy(
                policyDto,
                PetMapper.mapPetDtoToPet(petDto)
            )
        }
    }
}
