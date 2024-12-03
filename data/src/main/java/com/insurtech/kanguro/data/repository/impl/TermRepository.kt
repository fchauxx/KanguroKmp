package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.mapper.AppLanguageMapper
import com.insurtech.kanguro.data.repository.ITermRepository
import com.insurtech.kanguro.data.source.PreferencesDataSource
import com.insurtech.kanguro.data.source.TermDataSource
import java.io.InputStream
import javax.inject.Inject
import com.insurtech.kanguro.data.Result.Error as ResultError
import com.insurtech.kanguro.data.Result.Success as ResultSuccess

class TermRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource,
    private val termDataSource: TermDataSource
) : ITermRepository {

    override suspend fun getTermPdf(language: AppLanguage): Result<InputStream> {
        try {
            val languageDto = AppLanguageMapper.mapAppLanguageToAppLanguageDto(
                preferencesDataSource.getPreferredLanguage()
            )
            val term = termDataSource.getTermPdf(languageDto).byteStream()
            return ResultSuccess(term)
        } catch (e: Exception) {
            return ResultError(e)
        }
    }
}
