package com.insurtech.kanguro.networking.api

import com.insurtech.kanguro.networking.dto.AppLanguageDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface KanguroTermApiService {

    @GET("api/term/downloadterm")
    suspend fun getTermPdf(
        @Query("preferencialLanguage") language: AppLanguageDto
    ): ResponseBody
}
