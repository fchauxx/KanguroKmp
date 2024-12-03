package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.ScheduledItemInputImageMapper
import com.insurtech.kanguro.data.repository.IRentersScheduledItemsRepository
import com.insurtech.kanguro.data.source.RentersScheduledItemsDataSource
import com.insurtech.kanguro.domain.model.ScheduledItemInputImage
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException
import javax.inject.Inject

class RentersScheduledItemsRepository @Inject constructor(
    private val rentersScheduledItemsRemoteDataSource: RentersScheduledItemsDataSource
) : IRentersScheduledItemsRepository {

    override suspend fun putImages(scheduledItemId: String, images: List<ScheduledItemInputImage>): Result<Unit> {
        return try {
            val scheduledItemInputImagesDto = ScheduledItemInputImageMapper
                .mapScheduledItemInputImageToScheduledItemInputImageDto(images)
            rentersScheduledItemsRemoteDataSource.putImages(scheduledItemId, scheduledItemInputImagesDto)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getScheduledItemType(): Flow<Result<List<ScheduledItemTypeModel>>> = flow {
        try {
            val result = rentersScheduledItemsRemoteDataSource.getScheduledItemType()
            if (result != null) {
                emit(Result.Success(result))
            } else {
                emit(Result.Error(NullPointerException()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
