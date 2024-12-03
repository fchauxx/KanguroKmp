package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.PreventiveCoverageInfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RefactoredKanguroPolicyApiService {

    @GET("api/policy/{id}")
    suspend fun getPolicyDetail(
        @Path("id") policyId: String
    ): PetPolicyViewModelDto

    @GET("api/policy/{policy_id}/coverage")
    suspend fun getPolicyCoverage(
        @Path("policy_id") policyId: String,
        @Query("reimbursement") reimbursement: Double,
        @Query("offerId") offerId: Int?
    ): List<PreventiveCoverageInfoDto>

    @GET("api/policy")
    suspend fun getPolicies(): List<PetPolicyViewModelDto>
}
