package com.insurtech.kanguro.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.insurtech.kanguro.common.enums.AppLanguage
import java.util.*

object LanguageUtils {

    const val SELECTED_LANGUAGE = "user_preferred_language"
    const val DEFAULT_LANGUAGE = "English"

    fun setLanguage(language: String, context: Context): Context {
        val newLocale = Locale.forLanguageTag(language)
        Locale.setDefault(newLocale)

        val configuration = context.resources.configuration
        configuration.setLocale(newLocale)
        configuration.setLayoutDirection(newLocale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.createConfigurationContext(configuration)
        }

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        return context
    }

    fun switchLanguage(preferences: SharedPreferences, currentActivity: FragmentActivity, selectedLanguage: AppLanguage? = null) {
        val setLanguage = selectedLanguage
            ?: if (preferences.preferredLanguage == AppLanguage.English) {
                AppLanguage.Spanish
            } else {
                AppLanguage.English
            }

        preferences.preferredLanguage = setLanguage
        currentActivity.recreate()
    }

    fun getCurrentLanguage(preferences: SharedPreferences, context: Context): AppLanguage {
        return preferences.preferredLanguage ?: AppLanguage.fromLanguage(context.resources.configuration.locale.language)
    }
}
