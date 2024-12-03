package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.AdditionalPartyDto
import com.insurtech.kanguro.networking.dto.RentersPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemEndorsementPricingDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemPricingInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto

interface RentersPolicyDataSource {

    suspend fun getScheduledItems(policyId: String): List<ScheduledItemViewModelDto>

    suspend fun deleteScheduledItem(policyId: String, scheduledItemId: String)

    suspend fun postScheduledItemPricing(
        id: String,
        body: ScheduledItemPricingInputModelDto
    ): ScheduledItemEndorsementPricingDto

    suspend fun postScheduleItem(
        id: String,
        body: ScheduledItemInputModelDto
    ): ScheduledItemViewModelDto

    suspend fun getPolicyById(
        id: String
    ): RentersPolicyViewModelDto

    suspend fun getPolicies(): List<RentersPolicyViewModelDto>

    suspend fun getAdditionalParties(policyId: String): List<AdditionalPartyDto>

    suspend fun deleteAdditionalParty(policyId: String, additionalPartyId: String)
}
