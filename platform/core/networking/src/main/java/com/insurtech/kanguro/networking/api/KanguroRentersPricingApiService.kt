package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.AdditionalCoverageDto
import com.insurtech.kanguro.networking.dto.AdditionalCoveragesEndorsementDto
import com.insurtech.kanguro.networking.dto.DwellingTypeDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleDto
import com.insurtech.kanguro.networking.dto.EndorsementsDeductibleItemDto
import com.insurtech.kanguro.networking.dto.EndorsementsLiabilityDto
import com.insurtech.kanguro.networking.dto.PersonalPropertyRangeDto
import com.insurtech.kanguro.networking.dto.PolicyEndorsementPricingDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface KanguroRentersPricingApiService {

    @GET("api/Renters/Policy/Pricing/Liabilities")
    suspend fun getLiabilities(
        @Query("State") state: String
    ): List<EndorsementsLiabilityDto>

    @GET("api/Renters/Policy/Pricing/Deductibles")
    suspend fun getDeductibles(
        @Query("State") state: String
    ): List<EndorsementsDeductibleDto>

    @GET("api/Renters/Policy/Pricing/DeductibleItems")
    suspend fun getDeductibleItems(
        @Query("State") state: String
    ): List<EndorsementsDeductibleItemDto>

    @GET("api/Renters/Policy/Pricing/PersonalProperty")
    suspend fun getPersonalProperty(): PersonalPropertyRangeDto

    @GET("api/Renters/Policy/Pricing/AdditionalCoverages")
    suspend fun getAdditionalCoverages(
        @Query("DwellingType") dwellingType: DwellingTypeDto,
        @Query("DeductibleId") deductibleId: Int,
        @Query("LiabilityId") liabilityId: Int,
        @Query("PersonalProperty") personalProperty: Double,
        @Query("State") state: String,
        @Query("ZipCode") zipCode: String,
        @Query("IsInsuranceRequired") isInsuranceRequired: Boolean
    ): List<AdditionalCoverageDto>

    @PUT("api/Renters/Policy/{id}/AdditionalCoverage")
    suspend fun putAdditionalCoverage(
        @Path("id") policyId: String,
        @Body body: AdditionalCoveragesEndorsementDto
    ): Response<Unit>

    @POST("api/Renters/Policy/{id}/AdditionalCoverage/Pricing")
    suspend fun postAdditionalCoveragePricing(
        @Path("id") policyId: String,
        @Body body: AdditionalCoveragesEndorsementDto
    ): PolicyEndorsementPricingDto
}
