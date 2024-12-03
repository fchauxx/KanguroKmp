package com.insurtech.kanguro.data.local

import android.content.SharedPreferences
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.data.source.PreferencesDataSource
import javax.inject.Inject

class PreferencesLocalDataSource @Inject constructor(
    private val preferences: SharedPreferences
) : PreferencesDataSource {

    override suspend fun getPreferredLanguage(): AppLanguage {
        return preferences.getString(PREF_PREFERRED_LANGUAGE, null)?.let { AppLanguage.valueOf(it) }
            ?: AppLanguage.English
    }

    override suspend fun setPreferredLanguage(language: AppLanguage): Boolean {
        val editor = preferences.edit()
        editor.putString(PREF_PREFERRED_LANGUAGE, language.name)
        return editor.commit()
    }

    companion object {
        private const val PREF_PREFERRED_LANGUAGE = "user_preferred_language"
    }
}
