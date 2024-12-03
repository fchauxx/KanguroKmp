package com.insurtech.kanguro.data.remote

import com.insurtech.kanguro.data.source.TermDataSource
import com.insurtech.kanguro.networking.api.KanguroTermApiService
import com.insurtech.kanguro.networking.dto.AppLanguageDto
import com.insurtech.kanguro.networking.transformer.managedExecution
import okhttp3.ResponseBody
import javax.inject.Inject

class TermRemoteDataSource @Inject constructor(
    private val termApiService: KanguroTermApiService
) : TermDataSource {

    override suspend fun getTermPdf(language: AppLanguageDto): ResponseBody = managedExecution {
        termApiService.getTermPdf(language)
    }
}
