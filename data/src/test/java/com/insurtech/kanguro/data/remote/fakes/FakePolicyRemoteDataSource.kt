package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.PolicyDataSource
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo

class FakePolicyRemoteDataSource : PolicyDataSource {

    private var preventiveCoverageInfos: List<PreventiveCoverageInfo>? = null
    private var policy: PetPolicy? = null
    private var exception: Exception? = null

    fun setPreventiveCoverageInfos(preventiveCoverageInfos: List<PreventiveCoverageInfo>) {
        this.preventiveCoverageInfos = preventiveCoverageInfos
    }

    fun setPolicy(policy: PetPolicy) {
        this.policy = policy
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ): List<PreventiveCoverageInfo> = preventiveCoverageInfos ?: throw exception!!

    override suspend fun getPolicyDetail(policyId: String): PetPolicy = policy ?: throw exception!!

    override suspend fun getPolicies(): List<PetPolicy> {
        TODO("Not yet implemented")
    }
}
