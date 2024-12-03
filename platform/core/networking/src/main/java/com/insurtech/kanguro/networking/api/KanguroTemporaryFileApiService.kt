package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.TemporaryFileDto
import com.insurtech.kanguro.networking.dto.TemporaryUploadModelDto
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface KanguroTemporaryFileApiService {

    @Multipart
    @POST("/api/TemporaryFile")
    suspend fun postTemporaryFile(@Part file: MultipartBody.Part): TemporaryFileDto

    @GET("/api/TemporaryFile/UploadUrl")
    suspend fun getTemporaryUploadModel(): TemporaryUploadModelDto
}
