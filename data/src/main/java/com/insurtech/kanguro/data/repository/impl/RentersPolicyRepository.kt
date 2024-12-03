package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.AdditionalPartyMapper
import com.insurtech.kanguro.data.mapper.PolicyMapper
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper.mapScheduledItemEndorsementPricingDtoToScheduledItemEndorsementPricing
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper.mapScheduledItemInputModelToScheduledItemInputModelDto
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper.mapScheduledItemPricingInputModelToScheduledItemPricingInputModelDto
import com.insurtech.kanguro.data.mapper.ScheduledItemMapper.mapScheduledItemViewModelDtoToScheduledItemViewModel
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.data.source.RentersPolicyDataSource
import com.insurtech.kanguro.domain.model.AdditionalParty
import com.insurtech.kanguro.domain.model.RentersPolicy
import com.insurtech.kanguro.domain.model.ScheduledItemEndorsementPricing
import com.insurtech.kanguro.domain.model.ScheduledItemInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemPricingInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException
import javax.inject.Inject

class RentersPolicyRepository @Inject constructor(
    private val rentersPolicyRemoteDataSource: RentersPolicyDataSource
) : IRentersPolicyRepository {

    override suspend fun getScheduledItems(policyId: String): Flow<Result<List<ScheduledItemViewModel>>> =
        flow {
            try {
                val scheduleItems = ScheduledItemMapper.mapScheduledItemsDtosToScheduledItems(
                    rentersPolicyRemoteDataSource.getScheduledItems(policyId)
                )
                emit(Result.Success(scheduleItems))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun deleteScheduledItem(
        policyId: String,
        scheduledItemId: String
    ): Result<Unit> {
        return try {
            rentersPolicyRemoteDataSource.deleteScheduledItem(policyId, scheduledItemId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postScheduledItemPricing(
        id: String,
        body: ScheduledItemPricingInputModel
    ): Flow<Result<ScheduledItemEndorsementPricing>> = flow {
        try {
            val mappedBody =
                mapScheduledItemPricingInputModelToScheduledItemPricingInputModelDto(body)

            val response = rentersPolicyRemoteDataSource.postScheduledItemPricing(id, mappedBody)

            val mappedResponse =
                mapScheduledItemEndorsementPricingDtoToScheduledItemEndorsementPricing(response)

            if (mappedResponse != null) {
                emit(Result.Success(mappedResponse))
            } else {
                emit(Result.Error(NullPointerException()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun postScheduleItem(
        id: String,
        body: ScheduledItemInputModel
    ): Flow<Result<ScheduledItemViewModel>> = flow {
        try {
            val mappedBody = mapScheduledItemInputModelToScheduledItemInputModelDto(body)

            val result = rentersPolicyRemoteDataSource.postScheduleItem(id, mappedBody)

            val mappedResult = mapScheduledItemViewModelDtoToScheduledItemViewModel(result)

            emit(Result.Success(mappedResult))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getPolicyById(id: String): Result<RentersPolicy> {
        return try {
            val result = PolicyMapper.mapPolicyViewModelDtoToPolicyViewModel(
                rentersPolicyRemoteDataSource.getPolicyById(id)
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

    override suspend fun getPolicies(): Result<List<RentersPolicy>> {
        return try {
            val result = rentersPolicyRemoteDataSource.getPolicies().mapNotNull {
                PolicyMapper.mapPolicyViewModelDtoToPolicyViewModel(it)
            }
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getAdditionalParties(policyId: String): Result<List<AdditionalParty>> {
        return try {
            val result = AdditionalPartyMapper.mapAdditionalPartiesDtoToAdditionalParties(
                rentersPolicyRemoteDataSource.getAdditionalParties(policyId)
            )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteAdditionalParty(
        policyId: String,
        additionalPartyId: String
    ): Result<Unit> {
        return try {
            rentersPolicyRemoteDataSource.deleteAdditionalParty(policyId, additionalPartyId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
