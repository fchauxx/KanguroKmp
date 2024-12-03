package com.insurtech.kanguro.analytics

import com.insurtech.kanguro.analytics.firebase.KanguroFirebaseAnalytics
import javax.inject.Inject

class AnalyticsService @Inject constructor(
    private val kanguroFirebaseAnalytics: KanguroFirebaseAnalytics
) : IAnalyticsService {

    override fun analyticsLogScreen(screenName: AnalyticsEnums.Screen) {
        kanguroFirebaseAnalytics.logEvent(screenName)
    }
}
