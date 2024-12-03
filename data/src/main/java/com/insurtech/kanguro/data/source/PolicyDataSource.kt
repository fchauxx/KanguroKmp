package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo

interface PolicyDataSource {

    suspend fun getPolicyCoverage(
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ): List<PreventiveCoverageInfo>

    suspend fun getPolicyDetail(policyId: String): PetPolicy

    suspend fun getPolicies(): List<PetPolicy>
}
