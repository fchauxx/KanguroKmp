package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.ScheduledItemInputImageDto
import com.insurtech.kanguro.networking.dto.ScheduledItemTypeModelDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface KanguroRentersScheduledItemsApiService {

    @PUT("api/Renters/ScheduledItems/{id}/Images")
    suspend fun putImages(
        @Path("id") id: String,
        @Body images: List<ScheduledItemInputImageDto>
    )

    @GET("api/Renters/ScheduledItems/Types")
    suspend fun getScheduledItemsTypes(): List<ScheduledItemTypeModelDto>
}
