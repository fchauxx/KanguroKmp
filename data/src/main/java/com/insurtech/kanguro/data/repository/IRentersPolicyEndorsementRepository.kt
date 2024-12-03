package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.PolicyEndorsementInput
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing

interface IRentersPolicyEndorsementRepository {

    suspend fun postPolicyEndorsement(
        policyId: String,
        policyEndorsementInput: PolicyEndorsementInput
    ): Result<Unit>

    suspend fun postPolicyEndorsementPricing(
        policyId: String,
        policyEndorsementInput: PolicyEndorsementInput
    ): Result<PolicyEndorsementPricing>
}
