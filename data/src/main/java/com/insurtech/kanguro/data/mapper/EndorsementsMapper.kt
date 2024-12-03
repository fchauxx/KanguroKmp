package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.DeductibleItemType
import com.insurtech.kanguro.domain.model.EndorsementsDeductible
import com.insurtech.kanguro.domain.model.EndorsementsDeductibleItem
import com.insurtech.kanguro.domain.model.EndorsementsLiability
import com.insurtech.kanguro.domain.model.InvoiceInterval
import com.insurtech.kanguro.domain.model.PersonalPropertyRange
import com.insurtech.kanguro.domain.model.PolicyEndorsementInput
import com.insurtech.kanguro.domain.model.PolicyEndorsementPricing
import com.insurtech.kanguro.networking.dto.DeductibleItemTypeDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleItemDto
import com.insurtech.kanguro.networking.dto.EndorsementsLiabilityDto
import com.insurtech.kanguro.networking.dto.InvoiceIntervalDto
import com.insurtech.kanguro.networking.dto.PersonalPropertyRangeDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementInputDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto

object EndorsementsMapper {

    private fun mapEndorsementLiabilityDtoToEndorsementsLiability(endorsementsLiabilityDto: EndorsementsLiabilityDto): EndorsementsLiability? {
        return EndorsementsLiability(
            id = endorsementsLiabilityDto.id ?: return null,
            value = endorsementsLiabilityDto.value ?: return null,
            isDefaultOption = endorsementsLiabilityDto.isDefaultOption
        )
    }

    fun mapEndorsementLiabilitiesDtoToEndorsementsLiabilities(endorsementsLiabilitiesDto: List<EndorsementsLiabilityDto>): List<EndorsementsLiability> {
        return endorsementsLiabilitiesDto.mapNotNull {
            mapEndorsementLiabilityDtoToEndorsementsLiability(
                it
            )
        }
    }

    private fun mapEndorsementDeductibleDtoToEndorsementsDeductible(endorsementsDeductibleDto: EndorsementsDeductibleDto): EndorsementsDeductible? {
        return EndorsementsDeductible(
            id = endorsementsDeductibleDto.id ?: return null,
            value = endorsementsDeductibleDto.value ?: return null,
            isDefaultOption = endorsementsDeductibleDto.isDefaultOption
        )
    }

    fun mapEndorsementDeductiblesDtoToEndorsementsDeductibles(endorsementsDeductiblesDto: List<EndorsementsDeductibleDto>): List<EndorsementsDeductible> {
        return endorsementsDeductiblesDto.mapNotNull {
            mapEndorsementDeductibleDtoToEndorsementsDeductible(
                it
            )
        }
    }

    private fun mapEndorsementsDeductibleItemDtoToEndorsementsDeductibleItem(
        endorsementsDeductibleDto: EndorsementsDeductibleItemDto
    ): EndorsementsDeductibleItem? {
        return EndorsementsDeductibleItem(
            type = when (endorsementsDeductibleDto.type) {
                DeductibleItemTypeDto.MedicalPayments -> DeductibleItemType.MedicalPayments
                DeductibleItemTypeDto.Hurricane -> DeductibleItemType.Hurricane
                DeductibleItemTypeDto.LossOfUse -> DeductibleItemType.LossOfUse
                null -> return null
            },
            value = endorsementsDeductibleDto.value ?: return null
        )
    }

    fun mapEndorsementsDeductibleItemsDtoToEndorsementsDeductibleItems(
        endorsementsDeductibleItemsDto: List<EndorsementsDeductibleItemDto>
    ): List<EndorsementsDeductibleItem> {
        return endorsementsDeductibleItemsDto.mapNotNull {
            mapEndorsementsDeductibleItemDtoToEndorsementsDeductibleItem(
                it
            )
        }
    }

    fun mapPolicyEndorsementPricingDtoToPolicyEndorsementPricing(policyEndorsementPricingDto: PolicyEndorsementPricingDto): PolicyEndorsementPricing? {
        return PolicyEndorsementPricing(
            billingCycle = when (policyEndorsementPricingDto.billingCycle) {
                InvoiceIntervalDto.YEARLY -> InvoiceInterval.YEARLY
                InvoiceIntervalDto.QUARTERLY -> InvoiceInterval.QUARTERLY
                InvoiceIntervalDto.MONTHLY -> InvoiceInterval.MONTHLY
                null -> return null
            },
            currentPolicyValue = policyEndorsementPricingDto.currentPolicyValue
                ?: return null,
            endorsementPolicyValue = policyEndorsementPricingDto.endorsementPolicyValue
                ?: return null,
            billingCycleCurrentPolicyValue = policyEndorsementPricingDto.billingCycleCurrentPolicyValue
                ?: return null,
            billingCycleEndorsementPolicyValue = policyEndorsementPricingDto.billingCycleEndorsementPolicyValue
                ?: return null,
            policyPriceDifferenceValue = policyEndorsementPricingDto.policyPriceDifferenceValue
                ?: return null,
            billingCyclePolicyPriceDifferenceValue = policyEndorsementPricingDto.billingCyclePolicyPriceDifferenceValue
                ?: return null
        )
    }

    fun mapPolicyEndorsementInputToPolicyEndorsementInputDto(policyEndorsementInput: PolicyEndorsementInput): PolicyEndorsementInputDto {
        return PolicyEndorsementInputDto(
            deductibleId = policyEndorsementInput.deductibleId,
            liabilityId = policyEndorsementInput.liabilityId,
            personalProperty = policyEndorsementInput.personalProperty
        )
    }

    fun mapPersonalPropertyRangeDtoToPersonalPropertyRange(personalPropertyRangeDto: PersonalPropertyRangeDto?): PersonalPropertyRange? {
        return PersonalPropertyRange(
            minimum = personalPropertyRangeDto?.minimum ?: return null,
            maximum = personalPropertyRangeDto.maximum ?: return null
        )
    }
}
