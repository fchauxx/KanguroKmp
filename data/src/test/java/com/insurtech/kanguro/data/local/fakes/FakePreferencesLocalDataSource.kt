package com.insurtech.kanguro.data.local.fakes

import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.source.PreferencesDataSource

class FakePreferencesLocalDataSource : PreferencesDataSource {

    private var preferredLanguage: AppLanguage? = null
    private var isPreferredLanguageSet: Boolean? = null
    private var exception: Exception? = null

    fun setFakePreferredLanguage(language: AppLanguage) {
        preferredLanguage = language
    }

    fun setPreferredLanguageSuccess(isSet: Boolean) {
        isPreferredLanguageSet = isSet
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getPreferredLanguage(): AppLanguage =
        preferredLanguage ?: throw exception!!

    override suspend fun setPreferredLanguage(language: AppLanguage): Boolean =
        isPreferredLanguageSet ?: throw exception!!
}
