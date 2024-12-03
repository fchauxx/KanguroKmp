package com.insurtech.kanguro.ui.scenes.selectProduct

import android.content.SharedPreferences
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.ui.scenes.webView.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectProductWebViewFragment : WebViewFragment() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.GetQuoteSelectProduct

    @Inject
    lateinit var preferences: SharedPreferences

    override fun getWebViewUrl(): String {
        if (preferences.preferredLanguage == AppLanguage.Spanish) {
            return "${BuildConfig.WEBSITE_URL}/es/select-product"
        }
        return "${BuildConfig.WEBSITE_URL}/select-product"
    }
}
