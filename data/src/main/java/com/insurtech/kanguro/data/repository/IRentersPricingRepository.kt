package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoveragesEndorsement
import com.insurtech.kanguro.domain.model.DwellingType
import com.insurtech.kanguro.domain.model.EndorsementsDeductible
import com.insurtech.kanguro.domain.model.EndorsementsDeductibleItem
import com.insurtech.kanguro.domain.model.EndorsementsLiability
import com.insurtech.kanguro.domain.model.PersonalPropertyRange
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing

interface IRentersPricingRepository {

    suspend fun getLiabilities(state: String): Result<List<EndorsementsLiability>>

    suspend fun getDeductibles(state: String): Result<List<EndorsementsDeductible>>

    suspend fun getDeductibleItems(state: String): Result<List<EndorsementsDeductibleItem>>

    suspend fun getPersonalProperty(): Result<PersonalPropertyRange>

    suspend fun getAdditionalCoverages(
        dwellingType: DwellingType,
        deductibleId: Int,
        liabilityId: Int,
        personalProperty: Double,
        state: String,
        zipCode: String,
        isInsuranceRequired: Boolean
    ): Result<List<AdditionalCoverage>>

    suspend fun putAdditionalCoverage(
        policyId: String,
        body: AdditionalCoveragesEndorsement
    ): Result<Unit>

    suspend fun postAdditionalCoveragePricing(
        policyId: String,
        body: AdditionalCoveragesEndorsement
    ): Result<PolicyEndorsementPricing>
}
