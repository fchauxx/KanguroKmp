package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.AdditionalCoverageDto
import com.insurtech.kanguro.networking.dto.AdditionalCoveragesEndorsementDto
import com.insurtech.kanguro.networking.dto.DwellingTypeDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleItemDto
import com.insurtech.kanguro.networking.dto.EndorsementsLiabilityDto
import com.insurtech.kanguro.networking.dto.PersonalPropertyRangeDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto

interface RentersPricingDataSource {

    suspend fun getLiabilities(state: String): List<EndorsementsLiabilityDto>

    suspend fun getDeductibles(state: String): List<EndorsementsDeductibleDto>

    suspend fun getDeductibleItems(state: String): List<EndorsementsDeductibleItemDto>

    suspend fun getPersonalProperty(): PersonalPropertyRangeDto

    suspend fun getAdditionalCoverages(
        dwellingType: DwellingTypeDto,
        deductibleId: Int,
        liabilityId: Int,
        personalProperty: Double,
        state: String,
        zipCode: String,
        isInsuranceRequired: Boolean
    ): List<AdditionalCoverageDto>

    suspend fun putAdditionalCoverage(
        policyId: String,
        body: AdditionalCoveragesEndorsementDto
    )

    suspend fun postAdditionalCoveragePricing(
        policyId: String,
        body: AdditionalCoveragesEndorsementDto
    ): PolicyEndorsementPricingDto
}
