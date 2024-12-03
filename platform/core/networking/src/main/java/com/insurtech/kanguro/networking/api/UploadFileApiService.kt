package com.insurtech.kanguro.networking.api

import java.io.File

interface UploadFileApiService {

    suspend fun putOnboardingVideoFile(url: String, blobType: String, videoFile: File): Boolean
}
