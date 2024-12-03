package com.insurtech.kanguro.core.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.insurtech.kanguro.common.enums.AppLanguage

private const val PREF_PREFERRED_LANGUAGE = "user_preferred_language"

var SharedPreferences.preferredLanguage: AppLanguage?
    get() = getString(PREF_PREFERRED_LANGUAGE, null)?.let { AppLanguage.valueOf(it) }
    set(value) {
        edit { putString(PREF_PREFERRED_LANGUAGE, value?.name) }
    }
