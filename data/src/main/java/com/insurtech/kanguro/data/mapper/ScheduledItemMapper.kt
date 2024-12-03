package com.insurtech.kanguro.data.mapper

import com.insurtech.kanguro.domain.model.InvoiceInterval
import com.insurtech.kanguro.domain.model.ScheduledItem
import com.insurtech.kanguro.domain.model.ScheduledItemEndorsementPricing
import com.insurtech.kanguro.domain.model.ScheduledItemInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemPricingInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemViewModel
import com.insurtech.kanguro.networking.dto.InvoiceIntervalDto
import com.insurtech.kanguro.networking.dto.ScheduledItemDto
import com.insurtech.kanguro.networking.dto.ScheduledItemEndorsementPricingDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemPricingInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto

object ScheduledItemMapper {

    fun mapScheduledItemViewModelDtoToScheduledItemViewModel(scheduledItemDto: ScheduledItemViewModelDto): ScheduledItemViewModel =
        ScheduledItemViewModel(
            id = scheduledItemDto.id,
            name = scheduledItemDto.name,
            type = scheduledItemDto.type,
            valuation = scheduledItemDto.valuation,
            images = scheduledItemDto.images?.map {
                ScheduledItemImageMapper.mapScheduleItemImageDtoToScheduleItemImage(
                    it
                )
            }
        )

    fun mapScheduledItemsDtosToScheduledItems(scheduledItemDtos: List<ScheduledItemViewModelDto>): List<ScheduledItemViewModel> =
        scheduledItemDtos.map { mapScheduledItemViewModelDtoToScheduledItemViewModel(it) }

    fun mapScheduledItemPricingInputModelToScheduledItemPricingInputModelDto(value: ScheduledItemPricingInputModel): ScheduledItemPricingInputModelDto {
        return ScheduledItemPricingInputModelDto(
            value.type,
            value.valuation
        )
    }

    fun mapScheduledItemDtoToScheduledItem(value: ScheduledItemDto?): ScheduledItem? {
        return ScheduledItem(
            value?.type ?: return null,
            value.valuation ?: return null,
            value.total ?: return null,
            value.intervalTotal ?: return null
        )
    }

    fun mapScheduledItemEndorsementPricingDtoToScheduledItemEndorsementPricing(value: ScheduledItemEndorsementPricingDto): ScheduledItemEndorsementPricing? {
        return ScheduledItemEndorsementPricing(
            mapInvoiceIntervalDtoToInvoiceInterval(value.billingCycle) ?: return null,
            value.currentPolicyValue ?: return null,
            value.endorsementPolicyValue ?: return null,
            value.billingCycleCurrentPolicyValue ?: return null,
            value.billingCycleEndorsementPolicyValue ?: return null,
            value.policyPriceDifferenceValue ?: return null,
            value.billingCyclePolicyPriceDifferenceValue ?: return null,
            mapScheduledItemDtoToScheduledItem(value.scheduledItem) ?: return null
        )
    }

    fun mapInvoiceIntervalDtoToInvoiceInterval(value: InvoiceIntervalDto?): InvoiceInterval? =
        when (value) {
            InvoiceIntervalDto.YEARLY -> InvoiceInterval.YEARLY
            InvoiceIntervalDto.QUARTERLY -> InvoiceInterval.QUARTERLY
            InvoiceIntervalDto.MONTHLY -> InvoiceInterval.MONTHLY
            else -> null
        }

    fun mapScheduledItemInputModelToScheduledItemInputModelDto(value: ScheduledItemInputModel): ScheduledItemInputModelDto {
        return ScheduledItemInputModelDto(
            value.name,
            value.type,
            value.valuation
        )
    }
}
