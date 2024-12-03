package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.AdditionalParty
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.domain.model.ScheduledItemEndorsementPricing
import com.insurtech.kanguro.domain.model.ScheduledItemInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemPricingInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemViewModel
import kotlinx.coroutines.flow.Flow

interface IRentersPolicyRepository {

    suspend fun getScheduledItems(policyId: String): Flow<Result<List<ScheduledItemViewModel>>>

    suspend fun deleteScheduledItem(policyId: String, scheduledItemId: String): Result<Unit>

    suspend fun postScheduledItemPricing(
        id: String,
        body: ScheduledItemPricingInputModel
    ): Flow<Result<ScheduledItemEndorsementPricing>>

    suspend fun postScheduleItem(
        id: String,
        body: ScheduledItemInputModel
    ): Flow<Result<ScheduledItemViewModel>>

    suspend fun getPolicyById(
        id: String
    ): Result<RentersPolicy>

    suspend fun getPolicies(): Result<List<RentersPolicy>>

    suspend fun getAdditionalParties(policyId: String): Result<List<AdditionalParty>>

    suspend fun deleteAdditionalParty(policyId: String, additionalPartyId: String): Result<Unit>
}
