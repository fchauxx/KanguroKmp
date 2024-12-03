package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.mapper.ScheduledItemsMapper
import com.insurtech.kanguro.data.source.RentersScheduledItemsDataSource
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.api.KanguroRentersScheduledItemsApiService
import com.insurtech.kanguro.networking.dto.ScheduledItemInputImageDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import javax.inject.Inject

class RentersScheduledItemsRemoteDataSource @Inject constructor(
    private val kanguroRentersScheduledItemsApiService: KanguroRentersScheduledItemsApiService
) : RentersScheduledItemsDataSource {

    override suspend fun putImages(scheduledItemId: String, images: List<ScheduledItemInputImageDto>) =
        managedExecution {
            kanguroRentersScheduledItemsApiService.putImages(scheduledItemId, images)
        }

    override suspend fun getScheduledItemType(): List<ScheduledItemTypeModel>? =
        managedExecution {
            ScheduledItemsMapper.mapScheduledItemDtoListToScheduledItemModelList(
                kanguroRentersScheduledItemsApiService.getScheduledItemsTypes()
            )
        }
}
