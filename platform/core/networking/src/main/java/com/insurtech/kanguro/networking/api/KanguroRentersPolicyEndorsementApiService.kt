package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.PolicyEndorsementInputDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface KanguroRentersPolicyEndorsementApiService {

    @POST("api/Renters/Policy/{id}")
    suspend fun postPolicyEndorsement(
        @Path("id")policyId: String,
        @Body policyEndorsementInputDto: PolicyEndorsementInputDto
    ): Response<Unit>

    @POST("api/Renters/Policy/{id}/Pricing")
    suspend fun postPolicyEndorsementPricing(
        @Path("id")policyId: String,
        @Body policyEndorsementInputDto: PolicyEndorsementInputDto
    ): PolicyEndorsementPricingDto
}
