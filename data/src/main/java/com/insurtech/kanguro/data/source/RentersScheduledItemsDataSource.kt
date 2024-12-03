package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.dto.ScheduledItemInputImageDto

interface RentersScheduledItemsDataSource {

    suspend fun putImages(scheduledItemId: String, images: List<ScheduledItemInputImageDto>)

    suspend fun getScheduledItemType(): List<ScheduledItemTypeModel>?
}
