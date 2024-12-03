package com.insurtech.kanguro.core.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.utils.LanguageUtils

abstract class FullscreenActivity<B : ViewBinding> : BaseActivity<B>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLayoutInsets()
    }

    override fun attachBaseContext(newBase: Context?) {
        val preferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(newBase!!)

        val preferredLanguage =
            preferences.getString(LanguageUtils.SELECTED_LANGUAGE, LanguageUtils.DEFAULT_LANGUAGE)
                ?: LanguageUtils.DEFAULT_LANGUAGE
        val appLanguage = AppLanguage.valueOf(preferredLanguage)

        val context = LanguageUtils.setLanguage(appLanguage.language, newBase)

        super.attachBaseContext(context)
    }

    private fun setupLayoutInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets: WindowInsetsCompat ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            binding.root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                rightMargin = insets.right
            }
            val consumedInsets = onProcessWindowInsets(insets)
            windowInsets.inset(Insets.subtract(insets, consumedInsets))
        }
    }

    protected open fun onProcessWindowInsets(insets: Insets) = insets
}
