package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.data.source.PolicyDataSource
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefactoredPolicyRepository @Inject constructor(
    private val policyRemoteDataSource: PolicyDataSource
) : IRefactoredPolicyRepository {

    override suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ): Flow<Result<List<PreventiveCoverageInfo>>> = flow {
        try {
            val coverageItems =
                policyRemoteDataSource.getPolicyCoverage(policyId, reimbursement, offerId)
            emit(Result.Success(coverageItems))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getPolicyDetail(policyId: String): Flow<Result<PetPolicy>> = flow {
        try {
            val policy = policyRemoteDataSource.getPolicyDetail(policyId)
            emit(Result.Success(policy))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override suspend fun getPolicies(): Result<List<PetPolicy>> {
        return try {
            val policies = policyRemoteDataSource.getPolicies()
            Result.Success(policies)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
