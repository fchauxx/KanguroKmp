package com.insurtech.kanguro.analytics.firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.insurtech.kanguro.analytics.AnalyticsEnums

open class KanguroFirebaseAnalytics(private val firebaseAnalytics: FirebaseAnalytics) {

    fun logEvent(screenName: AnalyticsEnums.Screen) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName.toString())
        }
    }
}
