package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.RentersPricingDataSource
import com.insurtech.kanguro.networking.api.KanguroRentersPricingApiService
import com.insurtech.kanguro.networking.dto.AdditionalCoverageDto
import com.insurtech.kanguro.networking.dto.AdditionalCoveragesEndorsementDto
import com.insurtech.kanguro.networking.dto.DwellingTypeDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleItemDto
import com.insurtech.kanguro.networking.dto.EndorsementsLiabilityDto
import com.insurtech.kanguro.networking.dto.PersonalPropertyRangeDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class RentersPricingRemoteDataSource @Inject constructor(
    private val kanguroRentersPricingApiService: KanguroRentersPricingApiService
) : RentersPricingDataSource {

    override suspend fun getLiabilities(state: String): List<EndorsementsLiabilityDto> =
        managedExecution {
            kanguroRentersPricingApiService.getLiabilities(state)
        }

    override suspend fun getDeductibles(state: String): List<EndorsementsDeductibleDto> =
        managedExecution {
            kanguroRentersPricingApiService.getDeductibles(state)
        }

    override suspend fun getDeductibleItems(state: String): List<EndorsementsDeductibleItemDto> =
        managedExecution {
            kanguroRentersPricingApiService.getDeductibleItems(state)
        }

    override suspend fun getPersonalProperty(): PersonalPropertyRangeDto =
        managedExecution {
            kanguroRentersPricingApiService.getPersonalProperty()
        }

    override suspend fun getAdditionalCoverages(
        dwellingType: DwellingTypeDto,
        deductibleId: Int,
        liabilityId: Int,
        personalProperty: Double,
        state: String,
        zipCode: String,
        isInsuranceRequired: Boolean
    ): List<AdditionalCoverageDto> =
        managedExecution {
            kanguroRentersPricingApiService.getAdditionalCoverages(
                dwellingType,
                deductibleId,
                liabilityId,
                personalProperty,
                state,
                zipCode,
                isInsuranceRequired
            )
        }

    override suspend fun putAdditionalCoverage(
        policyId: String,
        body: AdditionalCoveragesEndorsementDto
    ): Unit =
        managedExecution {
            kanguroRentersPricingApiService.putAdditionalCoverage(policyId, body)
        }

    override suspend fun postAdditionalCoveragePricing(
        policyId: String,
        body: AdditionalCoveragesEndorsementDto
    ): PolicyEndorsementPricingDto =
        managedExecution {
            kanguroRentersPricingApiService.postAdditionalCoveragePricing(policyId, body)
        }
}
