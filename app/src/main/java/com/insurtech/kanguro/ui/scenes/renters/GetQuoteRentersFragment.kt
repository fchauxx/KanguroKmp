package com.insurtech.kanguro.ui.scenes.renters

import android.content.SharedPreferences
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.preferredLanguage
import com.insurtech.kanguro.ui.scenes.webView.WebViewFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GetQuoteRentersFragment : WebViewFragment() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.GetQuoteRenters

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var sessionManager: ISessionManager

    override fun getWebViewUrl(): String {
        val lang = (preferences.preferredLanguage ?: AppLanguage.English).language

        return if (sessionManager.sessionInfo != null) {
            "${BuildConfig.WEBSITE_RENTERS_URL}/$lang/quote/account-info?authToken=${sessionManager.sessionInfo!!.accessToken}&refreshToken=${sessionManager.sessionInfo!!.refreshToken}"
        } else {
            "${BuildConfig.WEBSITE_RENTERS_URL}/$lang/quote/account-info"
        }
    }
}
