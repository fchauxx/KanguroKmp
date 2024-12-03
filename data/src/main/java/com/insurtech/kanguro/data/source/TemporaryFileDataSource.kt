package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.TemporaryFileDto
import com.insurtech.kanguro.networking.dto.TemporaryUploadModelDto
import java.io.File

interface TemporaryFileDataSource {

    suspend fun postTemporaryFile(file: File): TemporaryFileDto

    suspend fun getTemporaryUploadModel(): TemporaryUploadModelDto

    suspend fun putOnboardingVideoFile(url: String, blobType: String, videoFile: File): Boolean
}
