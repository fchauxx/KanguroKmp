package com.insurtech.kanguro.data.remote.fakes

import com.insurtech.kanguro.networking.api.UploadFileApiService
import java.io.File

class FakeUploadFileApiService : UploadFileApiService {

    override suspend fun putOnboardingVideoFile(
        url: String,
        blobType: String,
        videoFile: File
    ): Boolean {
        return true
    }
}
