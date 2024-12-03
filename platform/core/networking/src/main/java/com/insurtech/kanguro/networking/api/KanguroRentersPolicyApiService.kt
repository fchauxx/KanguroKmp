package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.AdditionalPartyDto
import com.insurtech.kanguro.networking.dto.RentersPolicyViewModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemEndorsementPricingDto
import com.insurtech.kanguro.networking.dto.ScheduledItemInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemPricingInputModelDto
import com.insurtech.kanguro.networking.dto.ScheduledItemViewModelDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KanguroRentersPolicyApiService {

    @GET("api/Renters/Policy/{id}/ScheduledItems")
    suspend fun getScheduleItems(
        @Path("id") id: String
    ): List<ScheduledItemViewModelDto>

    @DELETE("api/Renters/Policy/{id}/ScheduledItem/{scheduledItemId}")
    suspend fun deleteScheduledItem(
        @Path("id") id: String,
        @Path("scheduledItemId") scheduledItemId: String
    )

    @POST("api/Renters/Policy/{id}/ScheduledItem/Pricing")
    suspend fun postScheduledItemPricing(
        @Path("id") id: String,
        @Body body: ScheduledItemPricingInputModelDto
    ): ScheduledItemEndorsementPricingDto

    @POST("api/Renters/Policy/{id}/ScheduledItem")
    suspend fun postScheduleItem(
        @Path("id") id: String,
        @Body body: ScheduledItemInputModelDto
    ): ScheduledItemViewModelDto

    @GET("api/Renters/Policy/{id}")
    suspend fun getPolicyById(
        @Path("id") id: String
    ): RentersPolicyViewModelDto

    @GET("api/Renters/Policy")
    suspend fun getPolicies(): List<RentersPolicyViewModelDto>

    @GET("api/Renters/Policy/{id}/AdditionalParties")
    suspend fun getAdditionalParties(@Path("id") policyId: String): List<AdditionalPartyDto>

    @DELETE("api/Renters/Policy/{id}/AdditionalParties/{additionalPartyId}")
    suspend fun deleteAdditionalParty(
        @Path("id") id: String,
        @Path("additionalPartyId") additionalPartyId: String
    )
}
