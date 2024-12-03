package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.TemporaryFileMapper
import com.insurtech.kanguro.data.mapper.TemporaryUploadModelMapper
import com.insurtech.kanguro.data.repository.ITemporaryFileRepository
import com.insurtech.kanguro.data.source.TemporaryFileDataSource
import com.insurtech.kanguro.domain.model.TemporaryFile
import com.insurtech.kanguro.domain.model.TemporaryUploadModel
import java.io.File
import javax.inject.Inject

class TemporaryFileRepository @Inject constructor(
    private val temporaryFileDataSource: TemporaryFileDataSource
) : ITemporaryFileRepository {

    override suspend fun postTemporaryFile(file: File): Result<TemporaryFile> {
        return try {
            val result = TemporaryFileMapper.mapTemporaryFileDtoToTemporaryFile(
                temporaryFileDataSource.postTemporaryFile(file)
            )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getTemporaryUploadModel(): Result<TemporaryUploadModel> {
        return try {
            val result =
                TemporaryUploadModelMapper.mapTemporaryUploadModelDtoToTemporaryUploadModel(
                    temporaryFileDataSource.getTemporaryUploadModel()
                )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun putOnboardingVideoFile(url: String, blobType: String, videoFile: File): Result<Unit> {
        return try {
            val isUploadSuccess = temporaryFileDataSource.putOnboardingVideoFile(url, blobType, videoFile)
            if (isUploadSuccess) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Error uploading video"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
