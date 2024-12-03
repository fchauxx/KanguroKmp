package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.RentersPolicyEndorsementDataSource
import com.insurtech.kanguro.networking.api.KanguroRentersPolicyEndorsementApiService
import com.insurtech.kanguro.networking.dto.PolicyEndorsementInputDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class RentersPolicyEndorsementRemoteDataSource @Inject constructor(
    private val kanguroRentersPolicyEndorsementApiService: KanguroRentersPolicyEndorsementApiService
) : RentersPolicyEndorsementDataSource {

    override suspend fun postPolicyEndorsement(
        policyId: String,
        policyEndorsementInputDto: PolicyEndorsementInputDto
    ): Unit = managedExecution {
        kanguroRentersPolicyEndorsementApiService.postPolicyEndorsement(
            policyId,
            policyEndorsementInputDto
        )
    }

    override suspend fun postPolicyEndorsementPricing(
        policyId: String,
        policyEndorsementInputDto: PolicyEndorsementInputDto
    ) = managedExecution {
        kanguroRentersPolicyEndorsementApiService.postPolicyEndorsementPricing(
            policyId,
            policyEndorsementInputDto
        )
    }
}
