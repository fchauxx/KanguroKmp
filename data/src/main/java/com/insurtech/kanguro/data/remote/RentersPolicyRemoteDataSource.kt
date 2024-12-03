package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.RentersPolicyDataSource
import com.insurtech.kanguro.networking.api.KanguroRentersPolicyApiService
import com.insurtech.kanguro.networking.dto.AdditionalPartyDto
import com.insurtech.kanguro.networking.dto.RentersPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemEndorsementPricingDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemPricingInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class RentersPolicyRemoteDataSource @Inject constructor(
    private val kanguroRentersPolicyApiService: KanguroRentersPolicyApiService
) : RentersPolicyDataSource {

    override suspend fun getScheduledItems(policyId: String): List<ScheduledItemViewModelDto> =
        managedExecution {
            kanguroRentersPolicyApiService.getScheduleItems(policyId)
        }

    override suspend fun deleteScheduledItem(policyId: String, scheduledItemId: String): Unit =
        managedExecution {
            kanguroRentersPolicyApiService.deleteScheduledItem(policyId, scheduledItemId)
        }

    override suspend fun postScheduledItemPricing(
        id: String,
        body: ScheduledItemPricingInputModelDto
    ): ScheduledItemEndorsementPricingDto =
        managedExecution {
            kanguroRentersPolicyApiService.postScheduledItemPricing(id, body)
        }

    override suspend fun postScheduleItem(
        id: String,
        body: ScheduledItemInputModelDto
    ): ScheduledItemViewModelDto =
        managedExecution {
            kanguroRentersPolicyApiService.postScheduleItem(id, body)
        }

    override suspend fun getPolicyById(id: String): RentersPolicyViewModelDto =
        managedExecution {
            kanguroRentersPolicyApiService.getPolicyById(id)
        }

    override suspend fun getPolicies(): List<RentersPolicyViewModelDto> =
        managedExecution {
            kanguroRentersPolicyApiService.getPolicies()
        }

    override suspend fun getAdditionalParties(policyId: String): List<AdditionalPartyDto> =
        managedExecution {
            kanguroRentersPolicyApiService.getAdditionalParties(policyId)
        }

    override suspend fun deleteAdditionalParty(policyId: String, additionalPartyId: String): Unit =
        managedExecution {
            kanguroRentersPolicyApiService.deleteAdditionalParty(policyId, additionalPartyId)
        }
}
