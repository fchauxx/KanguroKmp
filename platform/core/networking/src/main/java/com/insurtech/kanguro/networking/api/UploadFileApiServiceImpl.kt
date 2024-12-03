package com.insurtech.kanguro.networking.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class UploadFileApiServiceImpl @Inject constructor() : UploadFileApiService {

    override suspend fun putOnboardingVideoFile(
        url: String,
        blobType: String,
        videoFile: File
    ): Boolean {
        val requestFile = videoFile.asRequestBody("application/octet-stream".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .header("x-ms-blob-type", blobType)
            .put(requestFile)
            .build()

        val uploadClient = OkHttpClient()
        val response = uploadClient.newCall(request).execute()

        if (response.isSuccessful.not()) {
            Timber.e("Fail to upload Onboarding Video File. Message: ${response.message}. Code: ${response.code}")
        }

        return response.isSuccessful
    }
}
