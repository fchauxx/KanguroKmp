package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import java.io.InputStream

interface ITermRepository {

    suspend fun getTermPdf(language: AppLanguage): Result<InputStream>
}
