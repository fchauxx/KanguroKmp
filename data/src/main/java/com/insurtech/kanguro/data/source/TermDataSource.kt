package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.networking.dto.AppLanguageDto
import okhttp3.ResponseBody

interface TermDataSource {

    suspend fun getTermPdf(language: AppLanguageDto): ResponseBody
}
