package com.insurtech.kanguro.ui.scenes.webView

import android.content.SharedPreferences
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.analytics.AnalyticsEnums
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BlogFragment : WebViewFragment() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Blog

    @Inject
    lateinit var preferences: SharedPreferences

    override fun getWebViewUrl(): String {
        return "${BuildConfig.WEBSITE_PET_URL}/blog"
    }
}
