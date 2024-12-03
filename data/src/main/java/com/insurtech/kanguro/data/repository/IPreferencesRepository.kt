package com.insurtech.kanguro.data.repository

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.Result

interface IPreferencesRepository {

    suspend fun getPreferredLanguage(): Result<AppLanguage>

    suspend fun setPreferredLanguage(language: AppLanguage): Result<Boolean>
}
