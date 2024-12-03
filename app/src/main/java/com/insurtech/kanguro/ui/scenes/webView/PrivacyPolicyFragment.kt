package com.insurtech.kanguro.ui.scenes.webView

import android.content.SharedPreferences
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.utils.preferredLanguage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PrivacyPolicyFragment : WebViewFragment() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.PrivacyPolicy

    @Inject
    lateinit var preferences: SharedPreferences

    override fun getWebViewUrl(): String {
        val lang = (preferences.preferredLanguage ?: AppLanguage.English).language
        return "${BuildConfig.WEBSITE_PET_URL}/$lang/privacy-policy"
    }
}
