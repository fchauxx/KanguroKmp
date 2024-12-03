package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.EndorsementsMapper
import com.insurtech.kanguro.data.mapper.EndorsementsMapper.mapPolicyEndorsementPricingDtoToPolicyEndorsementPricing
import com.insurtech.kanguro.data.mapper.PolicyMapper.mapAdditionalCoverageDtoToAdditionalCoverage
import com.insurtech.kanguro.data.mapper.PolicyMapper.mapAdditionalCoveragesEndorsementToAdditionalCoveragesEndorsementDto
import com.insurtech.kanguro.data.mapper.PolicyMapper.mapDwellingTypeToDwellingTypeDto
import com.insurtech.kanguro.data.repository.IRentersPricingRepository
import com.insurtech.kanguro.data.source.RentersPricingDataSource
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoveragesEndorsement
import com.insurtech.kanguro.domain.model.DwellingType
import com.insurtech.kanguro.domain.model.EndorsementsDeductible
import com.insurtech.kanguro.domain.model.EndorsementsDeductibleItem
import com.insurtech.kanguro.domain.model.EndorsementsLiability
import com.insurtech.kanguro.domain.model.PersonalPropertyRange
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing
import javax.inject.Inject

class RentersPricingRepository @Inject constructor(
    private val rentersPricingDataSource: RentersPricingDataSource

) : IRentersPricingRepository {
    override suspend fun getLiabilities(state: String): Result<List<EndorsementsLiability>> {
        return try {
            val result = EndorsementsMapper.mapEndorsementLiabilitiesDtoToEndorsementsLiabilities(
                rentersPricingDataSource.getLiabilities(state)
            )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getDeductibles(state: String): Result<List<EndorsementsDeductible>> {
        return try {
            val result = EndorsementsMapper.mapEndorsementDeductiblesDtoToEndorsementsDeductibles(
                rentersPricingDataSource.getDeductibles(state)
            )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getDeductibleItems(state: String): Result<List<EndorsementsDeductibleItem>> {
        return try {
            val result =
                EndorsementsMapper.mapEndorsementsDeductibleItemsDtoToEndorsementsDeductibleItems(
                    rentersPricingDataSource.getDeductibleItems(state)
                )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPersonalProperty(): Result<PersonalPropertyRange> {
        return try {
            val result = EndorsementsMapper.mapPersonalPropertyRangeDtoToPersonalPropertyRange(
                rentersPricingDataSource.getPersonalProperty()
            )
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAdditionalCoverages(
        dwellingType: DwellingType,
        deductibleId: Int,
        liabilityId: Int,
        personalProperty: Double,
        state: String,
        zipCode: String,
        isInsuranceRequired: Boolean
    ): Result<List<AdditionalCoverage>> {
        return try {
            val result = rentersPricingDataSource.getAdditionalCoverages(
                mapDwellingTypeToDwellingTypeDto(dwellingType),
                deductibleId,
                liabilityId,
                personalProperty,
                state,
                zipCode,
                isInsuranceRequired
            )

            val resultMapped = result.mapNotNull {
                mapAdditionalCoverageDtoToAdditionalCoverage(it)
            }

            Result.Success(resultMapped)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun putAdditionalCoverage(
        policyId: String,
        body: AdditionalCoveragesEndorsement
    ): Result<Unit> {
        return try {
            val bodyMapped =
                mapAdditionalCoveragesEndorsementToAdditionalCoveragesEndorsementDto(
                    body
                )
            rentersPricingDataSource.putAdditionalCoverage(policyId, bodyMapped)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postAdditionalCoveragePricing(
        policyId: String,
        body: AdditionalCoveragesEndorsement
    ): Result<PolicyEndorsementPricing> {
        return try {
            val bodyMapped =
                mapAdditionalCoveragesEndorsementToAdditionalCoveragesEndorsementDto(
                    body
                )
            val result = mapPolicyEndorsementPricingDtoToPolicyEndorsementPricing(
                rentersPricingDataSource.postAdditionalCoveragePricing(
                    policyId,
                    bodyMapped
                )
            )
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
