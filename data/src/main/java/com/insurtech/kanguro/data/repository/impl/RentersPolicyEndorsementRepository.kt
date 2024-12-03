package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.EndorsementsMapper
import com.insurtech.kanguro.data.repository.IRentersPolicyEndorsementRepository
import com.insurtech.kanguro.data.source.RentersPolicyEndorsementDataSource
import com.insurtech.kanguro.domain.model.PolicyEndorsementInput
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing
import java.lang.NullPointerException
import javax.inject.Inject

class RentersPolicyEndorsementRepository @Inject constructor(
    private val rentersPolicyEndorsementDataSource: RentersPolicyEndorsementDataSource
) : IRentersPolicyEndorsementRepository {

    override suspend fun postPolicyEndorsement(
        policyId: String,
        policyEndorsementInput: PolicyEndorsementInput
    ): Result<Unit> {
        return try {
            val policyEndorsementInputDto =
                EndorsementsMapper.mapPolicyEndorsementInputToPolicyEndorsementInputDto(
                    policyEndorsementInput
                )

            rentersPolicyEndorsementDataSource.postPolicyEndorsement(
                policyId,
                policyEndorsementInputDto
            )

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postPolicyEndorsementPricing(
        policyId: String,
        policyEndorsementInput: PolicyEndorsementInput
    ): Result<PolicyEndorsementPricing> {
        return try {
            val policyEndorsementInputDto =
                EndorsementsMapper.mapPolicyEndorsementInputToPolicyEndorsementInputDto(
                    policyEndorsementInput
                )

            val result =
                EndorsementsMapper.mapPolicyEndorsementPricingDtoToPolicyEndorsementPricing(
                    rentersPolicyEndorsementDataSource.postPolicyEndorsementPricing(
                        policyId,
                        policyEndorsementInputDto
                    )
                )

            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(NullPointerException("Error getting policy endorsement pricing"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
