package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.PolicyEndorsementInputDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto

interface RentersPolicyEndorsementDataSource {
    suspend fun postPolicyEndorsement(
        policyId: String,
        policyEndorsementInputDto: PolicyEndorsementInputDto
    )

    suspend fun postPolicyEndorsementPricing(
        policyId: String,
        policyEndorsementInputDto: PolicyEndorsementInputDto
    ): PolicyEndorsementPricingDto
}
