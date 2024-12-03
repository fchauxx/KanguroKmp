package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.data.source.RentersScheduledItemsDataSource
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.dto.ScheduledItemInputImageDto

class FakeRentersScheduledItemsDataSource : RentersScheduledItemsDataSource {

    private var exception: Exception? = null

    fun setException(exception: Exception) {
        this.exception = exception
    }
    override suspend fun putImages(scheduledItemId: String, images: List<ScheduledItemInputImageDto>) {
        exception?.let { throw it }
    }

    override suspend fun getScheduledItemType(): List<ScheduledItemTypeModel>? {
        return null
    }
}
