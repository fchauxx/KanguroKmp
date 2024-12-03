package com.insurtech.kanguro.data.repository.impl

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IPreferencesRepository
import com.insurtech.kanguro.data.source.PreferencesDataSource
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val preferencesLocalDataSource: PreferencesDataSource
) : IPreferencesRepository {

    override suspend fun getPreferredLanguage(): Result<AppLanguage> {
        return try {
            val language = preferencesLocalDataSource.getPreferredLanguage()
            Result.Success(language)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun setPreferredLanguage(language: AppLanguage): Result<Boolean> {
        return try {
            val isSetSucceeded = preferencesLocalDataSource.setPreferredLanguage(language)
            Result.Success(isSetSucceeded)
        } catch (e: Exception) {
            Result.Success(false)
        }
    }
}
