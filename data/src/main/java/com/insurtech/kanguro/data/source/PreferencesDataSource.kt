package com.insurtech.kanguro.data.source

import com.insurtech.kanguro.common.enums.AppLanguage

interface PreferencesDataSource {

    suspend fun getPreferredLanguage(): AppLanguage

    suspend fun setPreferredLanguage(language: AppLanguage): Boolean
}
