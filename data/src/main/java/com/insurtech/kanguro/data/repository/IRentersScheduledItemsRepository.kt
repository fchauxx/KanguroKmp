package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.ScheduledItemInputImage
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import kotlinx.coroutines.flow.Flow

interface IRentersScheduledItemsRepository {

    suspend fun putImages(scheduledItemId: String, images: List<ScheduledItemInputImage>): Result<Unit>

    suspend fun getScheduledItemType(): Flow<Result<List<ScheduledItemTypeModel>>>
}
