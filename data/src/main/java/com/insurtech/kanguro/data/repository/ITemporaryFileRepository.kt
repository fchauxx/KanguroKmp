package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.domain.model.TemporaryFile
import com.insurtech.kanguro.domain.model.TemporaryUploadModel
import java.io.File

interface ITemporaryFileRepository {

    suspend fun postTemporaryFile(file: File): Result<TemporaryFile>

    suspend fun getTemporaryUploadModel(): Result<TemporaryUploadModel>

    suspend fun putOnboardingVideoFile(url: String, blobType: String, videoFile: File): Result<Unit>
}
