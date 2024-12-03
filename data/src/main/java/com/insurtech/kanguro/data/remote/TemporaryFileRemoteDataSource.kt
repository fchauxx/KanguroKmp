package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.TemporaryFileDataSource
import com.insurtech.kanguro.networking.api.KanguroTemporaryFileApiService
import com.insurtech.kanguro.networking.api.UploadFileApiService
import com.insurtech.kanguro.networking.dto.TemporaryFileDto
import com.insurtech.kanguro.networking.dto.TemporaryUploadModelDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class TemporaryFileRemoteDataSource @Inject constructor(
    private val kanguroTemporaryFileApiService: KanguroTemporaryFileApiService,
    private val uploadFileApiService: UploadFileApiService
) : TemporaryFileDataSource {

    override suspend fun postTemporaryFile(file: File): TemporaryFileDto = managedExecution {
        val requestFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

        kanguroTemporaryFileApiService.postTemporaryFile(multipartBody)
    }

    override suspend fun getTemporaryUploadModel(): TemporaryUploadModelDto = managedExecution {
        kanguroTemporaryFileApiService.getTemporaryUploadModel()
    }

    override suspend fun putOnboardingVideoFile(url: String, blobType: String, videoFile: File): Boolean =
        managedExecution {
            uploadFileApiService.putOnboardingVideoFile(url, blobType, videoFile)
        }
}
