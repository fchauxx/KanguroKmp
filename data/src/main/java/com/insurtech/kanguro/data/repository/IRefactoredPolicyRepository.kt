package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import kotlinx.coroutines.flow.Flow

interface IRefactoredPolicyRepository {

    suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int? = null
    ): Flow<Result<List<PreventiveCoverageInfo>>>

    suspend fun getPolicyDetail(policyId: String): Flow<Result<PetPolicy>>

    suspend fun getPolicies(): Result<List<PetPolicy>>
}
