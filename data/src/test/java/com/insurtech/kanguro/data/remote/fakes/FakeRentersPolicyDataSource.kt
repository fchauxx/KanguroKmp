package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.RentersPolicyDataSource
import com.insurtech.kanguro.networking.dto.AdditionalPartyDto
import com.insurtech.kanguro.networking.dto.RentersPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemEndorsementPricingDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemPricingInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto

class FakeRentersPolicyDataSource : RentersPolicyDataSource {

    private var scheduledItems: List<ScheduledItemViewModelDto>? = null
    private var additionalParties: List<AdditionalPartyDto>? = null
    private var exception: Exception? = null

    fun setScheduledItems(scheduledItems: List<ScheduledItemViewModelDto>) {
        this.scheduledItems = scheduledItems
    }

    fun setAdditionalParties(additionalParties: List<AdditionalPartyDto>) {
        this.additionalParties = additionalParties
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getScheduledItems(policyId: String): List<ScheduledItemViewModelDto> =
        scheduledItems ?: throw exception!!

    override suspend fun deleteScheduledItem(policyId: String, scheduledItemId: String) {
        exception?.let { throw it }
    }

    override suspend fun postScheduledItemPricing(
        id: String,
        body: ScheduledItemPricingInputModelDto
    ): ScheduledItemEndorsementPricingDto {
        throw exception!!
    }

    override suspend fun getPolicyById(id: String): RentersPolicyViewModelDto {
        throw exception!!
    }

    override suspend fun postScheduleItem(
        id: String,
        body: ScheduledItemInputModelDto
    ): ScheduledItemViewModelDto {
        throw exception!!
    }

    override suspend fun getPolicies(): List<RentersPolicyViewModelDto> {
        throw exception!!
    }

    override suspend fun getAdditionalParties(policyId: String): List<AdditionalPartyDto> =
        additionalParties ?: throw exception!!

    override suspend fun deleteAdditionalParty(policyId: String, additionalPartyId: String) {
        if (exception != null) throw exception!!
    }
}
