package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.data.mapper.AddressModelMapper.mapAddressModelDtoToAddressModel
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoverageType
import com.insurtech.kanguro.domain.model.AdditionalCoveragesEndorsement
import com.insurtech.kanguro.domain.model.DwellingType
import com.insurtech.kanguro.domain.model.InvoiceInterval
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PlanSummaryItem
import com.insurtech.kanguro.domain.model.PolicyAdditionalCoverageModel
import com.insurtech.kanguro.domain.model.PolicyPlanSummaryModel
import com.insurtech.kanguro.domain.model.PolicyPricingPaymentSimulation
import com.insurtech.kanguro.domain.model.PolicyStatus
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.networking.dto.AdditionalCoverageDto
import com.insurtech.kanguro.networking.dto.AdditionalCoverageTypeDto
import com.insurtech.kanguro.networking.dto.AdditionalCoveragesEndorsementDto
import com.insurtech.kanguro.networking.dto.DwellingTypeDto
import com.insurtech.kanguro.networking.dto.InvoiceIntervalDto
import com.insurtech.kanguro.networking.dto.PetPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.PlanSummaryItemDto
import com.insurtech.kanguro.networking.dto.PolicyAdditionalCoverageModelDto
import com.insurtech.kanguro.networking.dto.PolicyPlanSummaryModelDto
import com.insurtech.kanguro.networking.dto.PolicyPricingPaymentSimulationDto
import com.insurtech.kanguro.networking.dto.PolicyStatusDto
import com.insurtech.kanguro.networking.dto.RentersPolicyViewModelDto

object PolicyMapper {

    fun mapPolicyDtoToPolicy(policyDto: PetPolicyViewModelDto, pet: Pet): PetPolicy =
        PetPolicy(
            id = policyDto.id,
            policyExternalId = policyDto.policyExternalId,
            pet = pet,
            policyOfferId = policyDto.policyOfferId,
            deductible = policyDto.deductible?.let {
                DeductibleMapper.mapDeductibleDtoToDeductible(it)
            },
            sumInsured = policyDto.sumInsured?.let {
                AmountInfoMapper.mapAmountInfoDtoToAmountInfo(it)
            },
            payment = policyDto.payment?.let {
                PaymentMapper.mapPaymentDtoToPayment(it)
            },
            preventive = policyDto.preventive,
            waitingPeriod = policyDto.waitingPeriod,
            waitingPeriodRemainingDays = policyDto.waitingPeriodRemainingDays ?: 0,
            reimbursement = policyDto.reimbursement?.let {
                ReimbursementMapper.mapReimbursementDtoToReimbursement(it)
            },
            startDate = policyDto.startDate,
            endDate = policyDto.endDate,
            status = policyDto.status,
            isFuture = policyDto.isFuture
        )

    fun mapPolicyViewModelDtoToPolicyViewModel(policyViewModelDto: RentersPolicyViewModelDto): RentersPolicy? {
        return RentersPolicy(
            id = policyViewModelDto.id ?: return null,
            policyExternalId = policyViewModelDto.policyExternalId,
            dwellingType = mapDwellingTypeDtoToDwellingType(policyViewModelDto.dwellingType)
                ?: return null,
            address = mapAddressModelDtoToAddressModel(policyViewModelDto.address) ?: return null,
            status = mapPolicyStatusDToToPolicyStatus(policyViewModelDto.status) ?: return null,
            createdAt = policyViewModelDto.createdAt ?: return null,
            startAt = policyViewModelDto.startAt ?: return null,
            endAt = policyViewModelDto.endAt,
            planSummary = mapPolicyPlanSummaryModelDtoToPolicyPlanSummaryModel(policyViewModelDto.planSummary)
                ?: return null,
            additionalCoverages = policyViewModelDto.additionalCoverages?.map {
                mapPolicyAdditionalCoverageModelDtoToPolicyAdditionalCoverageModel(
                    it
                ) ?: return null
            },
            payment = mapPolicyPricingPaymentSimulationDtoToPolicyPricingPaymentSimulation(
                policyViewModelDto.payment
            ) ?: return null,
            onboardingCompleted = policyViewModelDto.onboardingCompleted ?: false,
            isInsuranceRequired = policyViewModelDto.isInsuranceRequired ?: false
        )
    }

    fun mapDwellingTypeDtoToDwellingType(dwellingTypeDto: DwellingTypeDto?): DwellingType? {
        return when (dwellingTypeDto) {
            DwellingTypeDto.SingleFamily -> DwellingType.SingleFamily
            DwellingTypeDto.MultiFamily -> DwellingType.MultiFamily
            DwellingTypeDto.Apartment -> DwellingType.Apartment
            DwellingTypeDto.Townhouse -> DwellingType.Townhouse
            DwellingTypeDto.StudentHousing -> DwellingType.StudentHousing
            null -> null
        }
    }

    fun mapDwellingTypeToDwellingTypeDto(dwellingTypeDto: DwellingType): DwellingTypeDto {
        return when (dwellingTypeDto) {
            DwellingType.SingleFamily -> DwellingTypeDto.SingleFamily
            DwellingType.MultiFamily -> DwellingTypeDto.MultiFamily
            DwellingType.Apartment -> DwellingTypeDto.Apartment
            DwellingType.Townhouse -> DwellingTypeDto.Townhouse
            DwellingType.StudentHousing -> DwellingTypeDto.StudentHousing
        }
    }

    fun mapPolicyStatusDToToPolicyStatus(policyStatusDto: PolicyStatusDto?): PolicyStatus? {
        return when (policyStatusDto) {
            PolicyStatusDto.ACTIVE -> PolicyStatus.ACTIVE
            PolicyStatusDto.PENDING -> PolicyStatus.PENDING
            PolicyStatusDto.CANCELED -> PolicyStatus.CANCELED
            PolicyStatusDto.TERMINATED -> PolicyStatus.TERMINATED
            null -> null
        }
    }

    fun mapPolicyPlanSummaryModelDtoToPolicyPlanSummaryModel(
        policyPlanSummaryModelDto: PolicyPlanSummaryModelDto?
    ): PolicyPlanSummaryModel? {
        if (policyPlanSummaryModelDto == null) return null
        return PolicyPlanSummaryModel(
            liability = mapPlanSummaryItemToPlanSummaryItem(policyPlanSummaryModelDto.liability)
                ?: return null,
            deductible = mapPlanSummaryItemToPlanSummaryItem(policyPlanSummaryModelDto.deductible)
                ?: return null,
            personalProperty = mapPlanSummaryItemToPlanSummaryItem(policyPlanSummaryModelDto.personalProperty)
                ?: return null,
            lossOfUse = mapPlanSummaryItemToPlanSummaryItem(policyPlanSummaryModelDto.lossOfUse)
                ?: return null
        )
    }

    fun mapPlanSummaryItemToPlanSummaryItem(planSummaryItemDto: PlanSummaryItemDto?): PlanSummaryItem? {
        if (planSummaryItemDto == null) return null
        return PlanSummaryItem(
            id = planSummaryItemDto.id ?: return null,
            value = planSummaryItemDto.value ?: return null
        )
    }

    fun mapPolicyAdditionalCoverageModelDtoToPolicyAdditionalCoverageModel(
        policyAdditionalCoverageModelDto: PolicyAdditionalCoverageModelDto?
    ): PolicyAdditionalCoverageModel? {
        if (policyAdditionalCoverageModelDto == null) return null
        return PolicyAdditionalCoverageModel(
            coverageLimit = policyAdditionalCoverageModelDto.coverageLimit ?: return null,
            deductibleLimit = policyAdditionalCoverageModelDto.deductibleLimit ?: return null,
            type = mapAdditionalCoverageTypeDtoToAdditionalCoverageTypeDto(
                policyAdditionalCoverageModelDto.type
            ) ?: return null
        )
    }

    fun mapAdditionalCoverageTypeDtoToAdditionalCoverageTypeDto(
        additionalCoverageTypeDto: AdditionalCoverageTypeDto?
    ): AdditionalCoverageType? {
        return when (additionalCoverageTypeDto) {
            AdditionalCoverageTypeDto.FraudProtection -> AdditionalCoverageType.FraudProtection
            AdditionalCoverageTypeDto.WaterSewerBackup -> AdditionalCoverageType.WaterSewerBackup
            AdditionalCoverageTypeDto.ReplacementCost -> AdditionalCoverageType.ReplacementCost
            null -> null
        }
    }

    fun mapPolicyPricingPaymentSimulationDtoToPolicyPricingPaymentSimulation(
        policyPricingPaymentSimulationDto: PolicyPricingPaymentSimulationDto?
    ): PolicyPricingPaymentSimulation? {
        if (policyPricingPaymentSimulationDto == null) return null
        return PolicyPricingPaymentSimulation(
            totalFees = policyPricingPaymentSimulationDto.totalFees ?: return null,
            totalPayment = policyPricingPaymentSimulationDto.totalPayment ?: return null,
            totalDiscounts = policyPricingPaymentSimulationDto.totalDiscounts ?: return null,
            totalPaymentWithoutFees = policyPricingPaymentSimulationDto.totalPaymentWithoutFees
                ?: return null,
            firstPayment = policyPricingPaymentSimulationDto.firstPayment ?: return null,
            recurringPayment = policyPricingPaymentSimulationDto.recurringPayment ?: return null,
            invoiceInterval = mapInvoiceIntervalDtoToInvoiceInterval(
                policyPricingPaymentSimulationDto.invoiceInterval
            ) ?: return null
        )
    }

    fun mapInvoiceIntervalDtoToInvoiceInterval(
        invoiceIntervalDto: InvoiceIntervalDto?
    ): InvoiceInterval? {
        return when (invoiceIntervalDto) {
            InvoiceIntervalDto.MONTHLY -> InvoiceInterval.MONTHLY
            InvoiceIntervalDto.QUARTERLY -> InvoiceInterval.QUARTERLY
            InvoiceIntervalDto.YEARLY -> InvoiceInterval.YEARLY
            null -> null
        }
    }

    fun mapAdditionalCoverageDtoToAdditionalCoverage(
        additionalCoverageDto: AdditionalCoverageDto?
    ): AdditionalCoverage? {
        if (additionalCoverageDto == null) return null
        return AdditionalCoverage(
            label = additionalCoverageDto.label ?: return null,
            intervalTotal = additionalCoverageDto.intervalTotal ?: return null,
            total = additionalCoverageDto.total ?: return null,
            coverageLimit = additionalCoverageDto.coverageLimit,
            deductibleLimit = additionalCoverageDto.deductibleLimit,
            type = mapAdditionalCoverageTypeDtoToAdditionalCoverageTypeDto(
                additionalCoverageDto.type
            ) ?: return null
        )
    }

    fun mapAdditionalCoveragesEndorsementToAdditionalCoveragesEndorsementDto(
        additionalCoveragesEndorsement: AdditionalCoveragesEndorsement
    ): AdditionalCoveragesEndorsementDto {
        return AdditionalCoveragesEndorsementDto(
            additionalCoverages = additionalCoveragesEndorsement.additionalCoverages.map {
                when (it) {
                    AdditionalCoverageType.ReplacementCost -> AdditionalCoverageTypeDto.ReplacementCost
                    AdditionalCoverageType.FraudProtection -> AdditionalCoverageTypeDto.FraudProtection
                    AdditionalCoverageType.WaterSewerBackup -> AdditionalCoverageTypeDto.WaterSewerBackup
                }
            }
        )
    }
}
